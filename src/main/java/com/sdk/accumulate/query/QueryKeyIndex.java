package com.sdk.accumulate.query;

public class QueryKeyIndex extends BaseQueryPrams {

    private String url;

    public QueryKeyIndex(String url) {
        this.url = url;
    }

    public QueryKeyIndex() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
