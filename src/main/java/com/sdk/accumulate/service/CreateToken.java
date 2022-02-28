package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.CreateTokenArg;

public class CreateToken extends BasePayload{



    private AccURL url;

    private AccURL keyBookUrl;

    private String symbol;

    private int precision;

    private AccURL properties;

    private int initialSupply;

    private boolean hasSupplyLimit;

    private AccURL manager;

    public CreateToken(CreateTokenArg createTokenArg) throws Exception {
        this.url = AccURL.toAccURL(createTokenArg.getUrl());
        this.keyBookUrl = AccURL.toAccURL(createTokenArg.getKeyBookUrl());
        this.symbol = createTokenArg.getSymbol();
        this.precision = createTokenArg.getPrecision();
        this.properties = AccURL.toAccURL(createTokenArg.getProperties());
        this.initialSupply = createTokenArg.getInitialSupply();
        this.hasSupplyLimit = createTokenArg.isHasSupplyLimit();
        this.manager = AccURL.toAccURL(createTokenArg.getManager());
    }

    public AccURL getUrl() {
        return url;
    }

    public void setUrl(AccURL url) {
        this.url = url;
    }

    public AccURL getKeyBookUrl() {
        return keyBookUrl;
    }

    public void setKeyBookUrl(AccURL keyBookUrl) {
        this.keyBookUrl = keyBookUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public AccURL getProperties() {
        return properties;
    }

    public void setProperties(AccURL properties) {
        this.properties = properties;
    }

    public int getInitialSupply() {
        return initialSupply;
    }

    public void setInitialSupply(int initialSupply) {
        this.initialSupply = initialSupply;
    }

    public boolean isHasSupplyLimit() {
        return hasSupplyLimit;
    }

    public void setHasSupplyLimit(boolean hasSupplyLimit) {
        this.hasSupplyLimit = hasSupplyLimit;
    }

    public AccURL getManager() {
        return manager;
    }

    public void setManager(AccURL manager) {
        this.manager = manager;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.CreateToken.getValue());
        byte[] urlBytes = Marshaller.stringMarshaller(this.url.string());
        byte[] keyBookUrlBytes = Marshaller.stringMarshaller(this.keyBookUrl.string());
        byte[] symbolBytes = Marshaller.stringMarshaller(this.symbol);
        byte[] precisionBytes = Marshaller.integerMarshaller(this.precision);
        byte[] propertyBytes = Marshaller.stringMarshaller(this.properties.string());
        byte[] supplyBytes = Marshaller.integerMarshaller(this.initialSupply);
        byte[] hasSupplyBytes = Marshaller.booleanMarshaller(this.hasSupplyLimit);
        byte[] managerBytes = Marshaller.stringMarshaller(this.manager.string());
        return Crypto.append(typeBytes,urlBytes,keyBookUrlBytes,symbolBytes,precisionBytes,propertyBytes,symbolBytes,supplyBytes,hasSupplyBytes,managerBytes);
    }

}
