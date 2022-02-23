package com.sdk.accumulate.model;

import java.util.List;

public class CreateKeyPageArg {

    private String url;

    private List<byte[]> keys;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<byte[]> getKeys() {
        return keys;
    }

    public void setKeys(List<byte[]> keys) {
        this.keys = keys;
    }
}
