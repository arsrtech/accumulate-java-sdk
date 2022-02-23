package com.sdk.accumulate.model;

public class CreateIdentityArg {

    private String url;

    private byte[] publicKey;

    private String keyBookName;

    private String keyPageName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getKeyBookName() {
        return keyBookName;
    }

    public void setKeyBookName(String keyBookName) {
        this.keyBookName = keyBookName;
    }

    public String getKeyPageName() {
        return keyPageName;
    }

    public void setKeyPageName(String keyPageName) {
        this.keyPageName = keyPageName;
    }
}
