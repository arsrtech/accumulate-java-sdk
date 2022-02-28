package com.sdk.accumulate.model;

public class TxnRequest {

    private String sponsor;

    private String origin;

    private Signer signer;

    private String signature;

    private KeyPage keyPage;

    private Object  payload;

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
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

    public KeyPage getKeyPage() {
        return keyPage;
    }

    public void setKeyPage(KeyPage keyPage) {
        this.keyPage = keyPage;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}

