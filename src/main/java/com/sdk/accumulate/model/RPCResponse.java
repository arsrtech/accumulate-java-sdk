package com.sdk.accumulate.model;

public class RPCResponse {

    private String jsonrpc;

    private ResponseResult result;

    private RPCError error;

    private long id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public ResponseResult getResult() {
        return result;
    }

    public void setResult(ResponseResult result) {
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RPCError getError() {
        return error;
    }

    public void setError(final RPCError error) {
        this.error = error;
    }
}

