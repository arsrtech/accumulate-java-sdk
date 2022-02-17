package com.sdk.accumulate.model;


public class RPCRequest {

    private String jsonrpc;

    private long id;

    private String method;

    private TxnRequest params;

    public RPCRequest(String jsonrpc, long id, String method, TxnRequest params) {
        this.jsonrpc = jsonrpc;
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public TxnRequest getParams() {
        return params;
    }

    public void setParams(TxnRequest params) {
        this.params = params;
    }
}
