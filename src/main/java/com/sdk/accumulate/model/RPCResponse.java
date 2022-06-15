package com.sdk.accumulate.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;

public class RPCResponse {

    private static final ObjectReader objectReader = new ObjectMapper().reader();

    private String jsonrpc;

    private JsonNode result;

    private RPCError error;

    private long id;

    public static RPCResponse from(final String json) {
        try {
            final RPCResponse rpcResponse = objectReader.readValue(json, RPCResponse.class);
            if (rpcResponse.getError() != null) {
                throw new RuntimeException(formatMessage(rpcResponse.getError()));
            }
            return rpcResponse;
        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize RPCResponse", e);
        }
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public TxResponse getResponseResult() {
        return null;
    }

    public void setResponseResult(TxResponse result) {
        this.result = null;
    }

    public JsonNode getResult() {
        return result;
    }

    public void setResult(final JsonNode resultTx) {
        this.result = resultTx;
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

    public boolean isTxPayload() {
        return result != null && result.has("txid");
    }

    public TxResponse asTxPayload() {
        if (!isTxPayload()) {
            throw new IllegalArgumentException("Response payload is not a transaction");
        }
        try {
            return objectReader.readValue(getResult(), TxResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize TxPayload", e);
        }
    }

    private static String formatMessage(final RPCError error) {
        if (error != null) {
            return String.format("RPC request failed: %s, %s", error.getMessage(), error.getData());
        }
        return null;
    }
}

