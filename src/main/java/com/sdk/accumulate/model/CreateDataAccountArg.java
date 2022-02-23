package com.sdk.accumulate.model;

public class CreateDataAccountArg {

    private String url;

    private String keyBookUrl;

    private String managerKeyBookUrl;

    private boolean scratch;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyBookUrl() {
        return keyBookUrl;
    }

    public void setKeyBookUrl(String keyBookUrl) {
        this.keyBookUrl = keyBookUrl;
    }

    public String getManagerKeyBookUrl() {
        return managerKeyBookUrl;
    }

    public void setManagerKeyBookUrl(String managerKeyBookUrl) {
        this.managerKeyBookUrl = managerKeyBookUrl;
    }

    public boolean isScratch() {
        return scratch;
    }

    public void setScratch(boolean scratch) {
        this.scratch = scratch;
    }
}
