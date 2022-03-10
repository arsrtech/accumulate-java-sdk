package com.sdk.accumulate.model;

public class Signer {

//    private String type;

    private String publicKey;

    private long nonce;

    public Signer() {
    }

    public Signer(String publicKey, long nonce) {
        this.publicKey = publicKey;
        this.nonce = nonce;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }
}
