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

    public Transaction(Payload payload, Header header, AccSignature signature) {
        this.payloadBinary = payload.marshalBinary();
        this.header = header;
        this.signature = signature;
    }

    public Transaction(Payload payload, Header header) {
        this.payloadBinary = payload.marshalBinary();
        this.header = header;

    }

    /**
     * Compute the hash of the transaction
     */
    public byte[] hash() throws NoSuchAlgorithmException {
        if (this.hash != null) {
            return this.hash;
        }
        byte[] sHash = sha256(this.header.marshalBinary());
        byte[] tHash = sha256(this.payloadBinary);
        this.hash = sha256(Crypto.append(sHash,tHash));
        return this.hash;
    }

    /**
     * Data that needs to be signed in order to submit the transaction.
     */
    public byte[] dataForSignature() throws NoSuchAlgorithmException {
        return Crypto.append(Marshaller.longMarshaller(this.header.getNonce()),this.hash());
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
        txnRequest.setOrigin(this.header.getOrigin().string());
        Signer signer = new Signer();
        signer.setPublicKey(Crypto.toHexString(this.signature.getPublicKey()));
        signer.setNonce(this.header.getNonce());
        txnRequest.setSigner(signer);
        txnRequest.setSignature(Crypto.toHexString(this.signature.getSignature()));
        KeyPage keyPage = new KeyPage();
        keyPage.setHeight(this.header.getKeyPageHeight());
//        keyPage.setIndex(this.header.getKeyPageIndex());
        txnRequest.setKeyPage(keyPage);
        txnRequest.setPayload(Crypto.toHexString(this.payloadBinary));
        return txnRequest;
    }
}