package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.CreateKeyPageArg;

import java.math.BigInteger;
import java.util.List;

public class CreateKeyPage extends BasePayload {

    private final AccURL url;

    private final List<byte[]> keys;

    public CreateKeyPage(CreateKeyPageArg createKeyPageArg) throws Exception {
        super();
        this.url = AccURL.toAccURL(createKeyPageArg.getUrl());
        this.keys = createKeyPageArg.getKeys();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateKeyPage.getValue())));
        byte[] urlBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.url.string()));
//        byte[] keyLengthBytes = Marshaller.integerMarshaller(this.keys.toArray().length);
        byte[] keyBytes = new byte[0];
        for (byte[] bytes: this.keys) {
            keyBytes = Crypto.append(keyBytes,Sequence.ONE,Marshaller.bytesMarshaller(bytes));
        }
        keyBytes = Crypto.append(Sequence.THREE,Marshaller.bytesMarshaller(keyBytes));
        return Crypto.append(typeBytes,urlBytes,keyBytes);
    }
}
