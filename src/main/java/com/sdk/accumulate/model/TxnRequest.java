package com.sdk.accumulate.model;

public class TxnRequest {

    private boolean checkOnly;

    private boolean isEnvelope;

    private String origin;

    private Signer signer;

    private String signature;

    private String txHash;

    private Object  payload;

    private String memo;

    private String metadata;

    public boolean isCheckOnly() {
        return checkOnly;
    }

    public void setCheckOnly(boolean checkOnly) {
        this.checkOnly = checkOnly;
    }

    public boolean isEnvelope() {
        return isEnvelope;
    }

    public void setEnvelope(boolean envelope) {
        isEnvelope = envelope;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Signer getSigner() {
        return signer;
    }

    public void setSigner(Signer signer) {
        this.signer = signer;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}

