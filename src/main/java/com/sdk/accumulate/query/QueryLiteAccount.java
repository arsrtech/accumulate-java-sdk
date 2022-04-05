package com.sdk.accumulate.query;

public class QueryLiteAccount extends BaseQueryPrams{

    private String url;

    public QueryLiteAccount(String url) {
        this.url = url;
    }

    public QueryLiteAccount() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
