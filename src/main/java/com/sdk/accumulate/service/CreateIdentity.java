package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.CreateIdentityArg;

public class CreateIdentity extends BasePayload{

    private String url;

    private byte[] publicKey;

    private String keyBookName;

    private String keyPageName;

    public CreateIdentity(CreateIdentityArg createIdentityArg) {
        super();
        this.url = createIdentityArg.getUrl();
        this.publicKey = createIdentityArg.getPublicKey();
        this.keyBookName = createIdentityArg.getKeyBookName();
        this.keyPageName = createIdentityArg.getKeyPageName();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public String getKeyBookName() {
        return keyBookName;
    }

    public void setKeyBookName(String keyBookName) {
        this.keyBookName = keyBookName;
    }

    public String getKeyPageName() {
        return keyPageName;
    }

    public void setKeyPageName(String keyPageName) {
        this.keyPageName = keyPageName;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.CreateIdentity.getValue());
        byte[] urlBytes = Marshaller.stringMarshaller(this.url);
        byte[] keyBookNameBytes = Marshaller.stringMarshaller(this.keyBookName);
        byte[] keyBookPageBytes = Marshaller.stringMarshaller(this.keyPageName);
        return Crypto.append(typeBytes,urlBytes,this.publicKey,keyBookNameBytes,keyBookPageBytes);
    }
}
