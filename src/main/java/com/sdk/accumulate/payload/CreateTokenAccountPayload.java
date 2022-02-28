package com.sdk.accumulate.payload;

public class CreateTokenAccountPayload {

    private String type;

    private String url;

    private String tokenUrl;

    private String keyBookUrl;

    private boolean scratch;

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

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getKeyBookUrl() {
        return keyBookUrl;
    }

    public void setKeyBookUrl(String keyBookUrl) {
        this.keyBookUrl = keyBookUrl;
    }

    public boolean isScratch() {
        return scratch;
    }

    public void setScratch(boolean scratch) {
        this.scratch = scratch;
    }
}
