package com.sdk.accumulate.query;

public class QueryTransaction extends BaseQueryPrams {

    private String txid;

    public QueryTransaction(String txid) {
        this.txid = txid;
    }

    public QueryTransaction() {
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}
