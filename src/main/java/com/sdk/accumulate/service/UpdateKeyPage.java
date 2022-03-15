package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.KeyPageOperation;
import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.UpdateKeyPageArg;

import java.math.BigInteger;

public class UpdateKeyPage extends BasePayload {

    private KeyPageOperation operation;

    private byte[] key;

    private byte[] newKey;

    private AccURL owner;

    private int threshold;

    public UpdateKeyPage(UpdateKeyPageArg updateKeyPageArg) throws Exception {
        super();
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

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.UpdateKeyPage.getValue())));
        byte[] operationBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.operation.name()));
        byte[] keyBytes = new byte[0];
        if (this.key != null)
            keyBytes = Crypto.append(Sequence.FIVE,Marshaller.bytesMarshaller(this.key));
        byte[] newKeyBytes = new byte[0];
        if (this.newKey != null)
            newKeyBytes = Crypto.append(Sequence.SIX,Marshaller.bytesMarshaller(this.newKey));
//        byte[] ownerBytes = Crypto.append(Sequence.FIVE,Marshaller.stringMarshaller(this.owner.string()));
//        byte[] thBytes = Crypto.append(Sequence.SIX,Marshaller.integerMarshaller(this.threshold));
        return Crypto.append(typeBytes,operationBytes,keyBytes,newKeyBytes);

    }
}
