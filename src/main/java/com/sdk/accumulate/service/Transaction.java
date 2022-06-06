package com.sdk.accumulate.service;

import com.sdk.accumulate.model.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static com.sdk.accumulate.service.Crypto.sha256;

public class Transaction {

    private Header header;
    private byte[] payloadBinary;
    private AccSignature signature;
    private byte[] hash;

    private final byte[] bodyHash;

    private SignerInfo signerInfo;

    public Transaction(Payload payload, Header header, AccSignature signature) {
        this.payloadBinary = payload.marshalBinary();
        this.header = header;
        this.signature = signature;
        this.bodyHash = payload.hash();
    }

    public Transaction(Payload payload, Header header) {
        this.payloadBinary = payload.marshalBinary();
        this.header = header;
        this.bodyHash = payload.hash();

    }

    /**
     * Compute the hash of the transaction
     */
    public byte[] hash() throws NoSuchAlgorithmException {
        if (this.hash != null) {
            return this.hash;
        }
        byte[] sHash = sha256(this.header.marshalBinary());
        this.hash = sha256(Crypto.append(sHash,this.bodyHash));
        return this.hash;
    }

    /**
     * Data that needs to be signed in order to submit the transaction.
     */
    public byte[] dataForSignature(SignerInfo signerInfo) throws NoSuchAlgorithmException {
        this.signerInfo = signerInfo;
        byte[] sigHash = this.header.computeInitiator(signerInfo);
        return Crypto.append(sigHash,this.hash());
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getPayloadBinary() {
        return payloadBinary;
    }

    public void setPayloadBinary(byte[] payloadBinary) {
        this.payloadBinary = payloadBinary;
    }

    public AccSignature getSignature() {
        return signature;
    }

    public void setSignature(AccSignature signature) {
        this.signature = signature;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }


    public void sign(OriginSigner signer) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        this.signature = signer.sign(this);
    }

    /**
     * Convert the Transaction into the param object for the `execute` API method
     */
    public TxnRequest toTxRequest(boolean checkOnly) {
        if (this.signature == null) {
            throw new Error("Unsigned transaction cannot be converted to TxRequest");
        }

        TxnRequest txnRequest = new TxnRequest();
        txnRequest.setCheckOnly(checkOnly);
        txnRequest.setEnvelope(false);
        txnRequest.setOrigin(this.header.getPrincipal().string());
        txnRequest.setSignature(Crypto.toHexString(this.signature.getSignature()));
        txnRequest.setTxHash(this.hash!=null?Crypto.toHexString(this.hash):null);
        txnRequest.setPayload(Crypto.toHexString(this.payloadBinary));
        txnRequest.setMemo(this.header.getMemo());
        txnRequest.setMetadata(this.header.getMetadata()!=null?Crypto.toHexString(this.header.getMetadata()):null);
        Signer signer = new Signer();
        signer.setUrl(this.signerInfo.getUrl());
        signer.setPublicKey(Crypto.toHexString(this.signerInfo.getPublicKey()));
        signer.setVersion(signerInfo.getVersion());
        signer.setTimestamp(this.header.getTimestamp());
        signer.setSignatureType("ed25519");
        signer.setUseSimpleHash(true);
        txnRequest.setSigner(signer);
        return txnRequest;
    }
}