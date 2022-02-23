package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.CreateKeyPageArg;

import java.util.List;

public class CreateKeyPage extends BasePayload {

    private AccURL url;

    private List<byte[]> keys;

    public CreateKeyPage(CreateKeyPageArg createKeyPageArg) throws Exception {
        this.url = AccURL.toAccURL(createKeyPageArg.getUrl());
        this.keys = createKeyPageArg.getKeys();
    }

    public AccURL getUrl() {
        return url;
    }

    public void setUrl(AccURL url) {
        this.url = url;
    }

    public List<byte[]> getKeys() {
        return keys;
    }

    public void setKeys(List<byte[]> keys) {
        this.keys = keys;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.CreateKeyPage.getValue());
        byte[] urlBytes = Marshaller.stringMarshaller(this.url.string());
        byte[] keyLengthBytes = Marshaller.integerMarshaller(this.keys.toArray().length);
        byte[] keyBytes = new byte[0];
        for (byte[] bytes: this.keys) {
            keyBytes = Crypto.append(keyBytes,bytes);
        }
        return Crypto.append(typeBytes,urlBytes,keyLengthBytes,keyBytes);
    }
}
