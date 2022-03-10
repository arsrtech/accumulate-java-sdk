package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.KeyPageOperation;
import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.UpdateKeyPageArg;

public class UpdateKeyPage extends BasePayload {

    private KeyPageOperation operation;

    private byte[] key;

    private byte[] newKey;

    private AccURL owner;

    private int threshold;

    public UpdateKeyPage(UpdateKeyPageArg updateKeyPageArg) throws Exception {
        this.operation = updateKeyPageArg.getOperation();
        switch (updateKeyPageArg.getOperation()) {
            case UpdateKey:
                if (updateKeyPageArg.getKey() == null)
                    throw new Error("old key to update missing");
                if (updateKeyPageArg.getNewKey() == null)
                    throw new Error("new key to update missing");
                break;
            case AddKey:
                if (updateKeyPageArg.getKey() != null)
                    throw new Error("key should not be set");
                if (updateKeyPageArg.getNewKey() ==null)
                    throw new Error("new key to add missing");
                break;
            case RemoveKey:
                if (updateKeyPageArg.getKey() == null)
                    throw new Error("key to remove missing");
                if (updateKeyPageArg.getNewKey() != null)
                    throw new Error("new key should not be set");
                break;
        }
        this.operation = updateKeyPageArg.getOperation();
        if (updateKeyPageArg.getKey() != null)
            this.key = updateKeyPageArg.getKey();
        if (updateKeyPageArg.getNewKey() != null)
            this.newKey = updateKeyPageArg.getNewKey();
        this.owner = AccURL.toAccURL(updateKeyPageArg.getOwner());
        this.threshold = updateKeyPageArg.getThreshold();
    }

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

    public AccURL getOwner() {
        return owner;
    }

    public void setOwner(AccURL owner) {
        this.owner = owner;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.UpdateKeyPage.getValue());
        byte[] operationBytes = Marshaller.stringMarshaller(this.operation.name());;
        byte[] keyBytes = new byte[0];
        if (this.key != null)
            keyBytes = this.key;
        byte[] newKeyBytes = new byte[0];
        if (this.newKey != null)
            newKeyBytes = this.newKey;
        byte[] ownerBytes = Marshaller.stringMarshaller(this.owner.string());
        byte[] thBytes = Marshaller.integerMarshaller(this.threshold);
        return Crypto.append(typeBytes,operationBytes,keyBytes,newKeyBytes,ownerBytes,thBytes);

    }
}
