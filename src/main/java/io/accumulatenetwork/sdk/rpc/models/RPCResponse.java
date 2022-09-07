package io.accumulatenetwork.sdk.rpc.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.generated.apiv2.TxResponse;
import io.accumulatenetwork.sdk.generated.errors.Status;
import io.accumulatenetwork.sdk.rpc.RPCException;
import io.accumulatenetwork.sdk.support.ResultReader;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RPCResponse {

    private String jsonrpc;

    private JsonNode result;

    private RPCError error;

    private long id;

    public static RPCResponse from(final String json) {
        final RPCResponse rpcResponse = ResultReader.readValue(json, RPCResponse.class);
        final RPCError error = rpcResponse.getError();
        if (error != null && error.getCode() != Status.OK.getValue()) {
            throw new RPCException(error.getCode(), formatMessage(error));
        }
        return rpcResponse;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }


    public JsonNode getResultNode() {
        return result;
    }

    public void setResult(final JsonNode value) {
        this.result = value;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
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

    public TxResponse asTransactionResponse() {
        if (!isTxPayload()) {
            throw new IllegalArgumentException("Response payload is not a transaction");
        }
        return ResultReader.readValue(getResultNode(), TxResponse.class);
    }

    private static String formatMessage(final RPCError error) {
        if (error != null) {
            return String.format("RPC request failed: %s, %s", error.getMessage(), error.getData());
        }
        return null;
    }
}

