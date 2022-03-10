package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.CreateTokenAccountArg;

public class CreateTokenAccount extends BasePayload {

    private AccURL url;

    private AccURL tokenUrl;

    private AccURL keyBookUrl;

    private boolean scratch;

    public CreateTokenAccount(CreateTokenAccountArg createTokenAccountArg) throws Exception {
        super();
        this.url = AccURL.toAccURL(createTokenAccountArg.getUrl());
        this.tokenUrl = AccURL.toAccURL(createTokenAccountArg.getTokenUrl());
        this.keyBookUrl = AccURL.toAccURL(createTokenAccountArg.getKeyBookUrl());
        this.scratch = createTokenAccountArg.isScratch();
    }

    public AccURL getUrl() {
        return url;
    }

    public void setUrl(AccURL url) {
        this.url = url;
    }

    public AccURL getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(AccURL tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public AccURL getKeyBookUrl() {
        return keyBookUrl;
    }

    public void setKeyBookUrl(AccURL keyBookUrl) {
        this.keyBookUrl = keyBookUrl;
    }

    public boolean isScratch() {
        return scratch;
    }

    public void setScratch(boolean scratch) {
        this.scratch = scratch;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.CreateTokenAccount.getValue());
        byte[] urlBytes = Marshaller.stringMarshaller(this.url.string());
        byte[] tokenUrlBytes = Marshaller.stringMarshaller(this.tokenUrl.string());
        byte[] keyBookUrlBytes = Marshaller.stringMarshaller(this.keyBookUrl.string());
        byte[] scratchBytes = Marshaller.booleanMarshaller(this.scratch);
        return Crypto.append(typeBytes,urlBytes,tokenUrlBytes,keyBookUrlBytes,scratchBytes);
    }
}
