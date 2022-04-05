package com.sdk.accumulate.model;

public class ResponseResult {

    private String transactionHash;

    private String txid;

    private String envelopeHash;

    private String simpleHash;

    private String hash;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getEnvelopeHash() {
        return envelopeHash;
    }

    public void setEnvelopeHash(String envelopeHash) {
        this.envelopeHash = envelopeHash;
    }

    public String getSimpleHash() {
        return simpleHash;
    }

    public void setSimpleHash(String simpleHash) {
        this.simpleHash = simpleHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
