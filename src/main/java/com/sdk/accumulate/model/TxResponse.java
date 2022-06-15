package com.sdk.accumulate.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class TxResponse {

    private String transactionHash;

    private List<String> signatureHashes = new ArrayList<>();

    private String txid;

    private String simpleHash;

    private String hash;

    private int code;

    private String message;

    private JsonNode result;


    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }


    public List<String> getSignatureHashes() {
        return signatureHashes;
    }

    public void setSignatureHashes(final List<String> signatureHashes) {
        this.signatureHashes = signatureHashes;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getSimpleHash() {
        return simpleHash;
    }

    public void setSimpleHash(String simpleHash) {
        this.simpleHash = simpleHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public JsonNode getResult() {
        return result;
    }

    public void setResult(final JsonNode result) {
        this.result = result;
    }
}
