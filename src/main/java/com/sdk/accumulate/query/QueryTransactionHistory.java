package com.sdk.accumulate.query;

public class QueryTransactionHistory extends BaseQueryPrams {

    private String url;

    private int count;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
