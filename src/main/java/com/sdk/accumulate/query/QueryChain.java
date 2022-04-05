package com.sdk.accumulate.query;

public class QueryChain extends BaseQueryPrams {

    private String chainId;

    public QueryChain(String chainId) {
        this.chainId = chainId;
    }

    public QueryChain() {
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }
}
