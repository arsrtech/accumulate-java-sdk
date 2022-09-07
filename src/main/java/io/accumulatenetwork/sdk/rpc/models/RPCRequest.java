package io.accumulatenetwork.sdk.rpc.models;


import com.fasterxml.jackson.databind.JsonNode;

public class RPCRequest {

    private String jsonrpc;

    private long id;

    private String method;

    private JsonNode params;

    public RPCRequest(final String jsonrpc, final long id, final String method, final JsonNode params) {
        this.jsonrpc = jsonrpc;
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(final String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(final JsonNode params) {
        this.params = params;
    }
}
