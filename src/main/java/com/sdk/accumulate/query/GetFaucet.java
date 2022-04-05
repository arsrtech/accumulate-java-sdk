package com.sdk.accumulate.query;

public class GetFaucet extends BaseQueryPrams{

    private String url;

    public GetFaucet(String url) {
        this.url = url;
    }

    public GetFaucet() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
