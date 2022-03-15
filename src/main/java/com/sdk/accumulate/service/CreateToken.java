package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.CreateTokenArg;

import java.math.BigInteger;

public class CreateToken extends BasePayload{

    private final AccURL url;

    private final AccURL keyBookUrl;

    private final String symbol;

    private final int precision;

    private final AccURL properties;

    private final BigInteger initialSupply;

    private final boolean hasSupplyLimit;

    private final AccURL manager;

    public CreateToken(CreateTokenArg createTokenArg) throws Exception {
        super();
        this.url = AccURL.toAccURL(createTokenArg.getUrl());
        this.keyBookUrl = AccURL.toAccURL(createTokenArg.getKeyBookUrl());
        this.symbol = createTokenArg.getSymbol();
        this.precision = createTokenArg.getPrecision();
        this.properties = AccURL.toAccURL(createTokenArg.getProperties());
        this.initialSupply = createTokenArg.getInitialSupply();
        this.hasSupplyLimit = createTokenArg.isHasSupplyLimit();
        this.manager = AccURL.toAccURL(createTokenArg.getManager());
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateToken.getValue())));
        byte[] urlBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.url.string()));
        byte[] keyBookUrlBytes = Crypto.append(Sequence.THREE,Marshaller.stringMarshaller(this.keyBookUrl.string()));
        byte[] symbolBytes = Crypto.append(Sequence.FOUR,Marshaller.stringMarshaller(this.symbol));
        byte[] precisionBytes = Crypto.append(Sequence.FIVE,Marshaller.integerMarshaller(this.precision));
        byte[] propertyBytes = Crypto.append(Sequence.SIX,Marshaller.stringMarshaller(this.properties.string()));
        byte[] supplyBytes = Crypto.append(Sequence.SEVEN,Marshaller.longMarshaller(this.initialSupply.longValue()));
        byte[] hasSupplyBytes = Crypto.append(Sequence.EIGHT,Marshaller.booleanMarshaller(this.hasSupplyLimit));
        byte[] managerBytes = Crypto.append(Sequence.NINE,Marshaller.stringMarshaller(this.manager.string()));
        return Crypto.append(typeBytes,urlBytes,keyBookUrlBytes,symbolBytes,precisionBytes,propertyBytes,symbolBytes,supplyBytes,hasSupplyBytes,managerBytes);
    }

}
