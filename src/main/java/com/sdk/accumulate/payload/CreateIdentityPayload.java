package com.sdk.accumulate.payload;

public class CreateIdentityPayload {

    private String type;

    private String url;

    private byte[] publicKey;

    private String keyBookName;

    private String keyPageName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
