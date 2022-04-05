package com.sdk.accumulate.query;

public class QueryData extends BaseQueryPrams {

    private String url;

    public QueryData(String url) {
        this.url = url;
    }

    public QueryData() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
