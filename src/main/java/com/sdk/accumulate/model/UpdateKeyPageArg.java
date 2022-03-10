package com.sdk.accumulate.model;

import com.sdk.accumulate.enums.KeyPageOperation;

public class UpdateKeyPageArg {

    private KeyPageOperation operation;

    private byte[] key;

    private byte[] newKey;

    private String owner;

    private int threshold;

    public KeyPageOperation getOperation() {
        return operation;
    }

    public void setOperation(KeyPageOperation operation) {
        this.operation = operation;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getNewKey() {
        return newKey;
    }

    public void setNewKey(byte[] newKey) {
        this.newKey = newKey;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
