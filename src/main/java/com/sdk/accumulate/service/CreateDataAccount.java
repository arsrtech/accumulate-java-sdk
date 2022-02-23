package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.CreateDataAccountArg;

public class CreateDataAccount extends BasePayload{

    private String url;

    private String keyBookUrl;

    private String managerKeyBookUrl;

    private boolean scratch;

    public CreateDataAccount(CreateDataAccountArg createDataAccountArg) {
        this.url = createDataAccountArg.getUrl();
        this.keyBookUrl = createDataAccountArg.getKeyBookUrl();
        this.managerKeyBookUrl = createDataAccountArg.getManagerKeyBookUrl();
        this.scratch = createDataAccountArg.isScratch();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyBookUrl() {
        return keyBookUrl;
    }

    public void setKeyBookUrl(String keyBookUrl) {
        this.keyBookUrl = keyBookUrl;
    }

    public String getManagerKeyBookUrl() {
        return managerKeyBookUrl;
    }

    public void setManagerKeyBookUrl(String managerKeyBookUrl) {
        this.managerKeyBookUrl = managerKeyBookUrl;
    }

    public boolean isScratch() {
        return scratch;
    }

    public void setScratch(boolean scratch) {
        this.scratch = scratch;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.CreateDataAccount.getValue());
        byte[] urlBytes = Marshaller.stringMarshaller(this.url);
        byte[] keyBookUrlBytes = Marshaller.stringMarshaller(this.keyBookUrl);
        byte[] managerKeyBookUrlBytes = Marshaller.stringMarshaller(this.managerKeyBookUrl);
        byte[] scratchBytes = Marshaller.booleanMarshaller(this.scratch);
        return Crypto.append(typeBytes,urlBytes,keyBookUrlBytes,managerKeyBookUrlBytes,scratchBytes);
    }
}
