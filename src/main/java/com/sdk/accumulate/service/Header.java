package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.model.HeaderOptions;

import javax.crypto.SealedObject;
import java.math.BigInteger;
import java.util.Date;

public class Header {

    private AccURL principal;

    private byte[] initiator;

    private String memo;

    private byte[] metadata;

    private long timestamp;

    public AccURL getPrincipal() {
        return principal;
    }

    public void setPrincipal(AccURL principal) {
        this.principal = principal;
    }

    public byte[] getInitiator() {
        return initiator;
    }

    public void setInitiator(byte[] initiator) {
        this.initiator = initiator;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public byte[] getMetadata() {
        return metadata;
    }

    public void setMetadata(byte[] metadata) {
        this.metadata = metadata;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Header(String principal, HeaderOptions options) throws Exception {
        this.principal = AccURL.toAccURL(principal);
        this.memo = options.getMemo()!=null?options.getMemo():null;
        this.metadata = options.getMetadata()!=null?options.getMetadata():null;
        this.timestamp = options.getTimestamp()==0?new Date().getTime():options.getTimestamp();
    }

    public byte[] marshalBinary() {
        byte[] principalBytes = Crypto.append(Sequence.ONE,Marshaller.stringMarshaller(this.principal.string()));
        byte[] initiatorBytes = Crypto.append(Sequence.TWO,initiator);
        byte[] memoBytes = new byte[0];
        if (this.memo != null) {
             memoBytes = Crypto.append(Sequence.THREE,Marshaller.stringMarshaller(this.memo));
        }
        byte[] metadataBytes = new byte[0];
        if (this.metadata != null) {
             metadataBytes = Crypto.append(Sequence.FOUR,Marshaller.bytesMarshaller(this.metadata));
        }
        return Crypto.append(principalBytes,initiatorBytes,memoBytes,metadataBytes);
    }

    public byte[] computeInitiator(SignerInfo signerInfo) {
        byte[] typeBinary = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(signerInfo.getType())));
        byte[] publicKeyBinary = Crypto.append(Sequence.TWO,Marshaller.bytesMarshaller(signerInfo.getPublicKey()));
        byte[] urlBinary = Crypto.append(Sequence.FOUR,Marshaller.stringMarshaller(signerInfo.getUrl()));
        byte[] versionBinary = Crypto.append(Sequence.FIVE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(signerInfo.getVersion())));
        byte[] timeStampBinary = Crypto.append(Sequence.SIX,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(this.timestamp)));
        this.initiator = Crypto.append(typeBinary,publicKeyBinary,urlBinary,versionBinary,timeStampBinary);
        return this.initiator;
    }
}

