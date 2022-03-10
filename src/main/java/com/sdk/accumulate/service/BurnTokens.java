package com.sdk.accumulate.service;

import com.sdk.accumulate.model.BurnTokensArg;
import com.sdk.accumulate.enums.TxnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class BurnTokens extends BasePayload{

    private static final Logger logger = LoggerFactory.getLogger(BurnTokens.class);

    private BigInteger amount;

    public BurnTokens(BurnTokensArg burnTokensArg) {
        super();
        this.amount = burnTokensArg.getAmount();
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public byte[] _marshalBinary() {
//        byte[] type = Marshaller.stringMarshaller(TxnType.BurnTokens.getValue());
        byte[] type = Marshaller.uvarintMarshalBinary(BigInteger.valueOf(10));
        byte[] a = {1};
        byte[] typeNew = Crypto.append(a,type);
        logger.info("Type Bytes: {}",Crypto.toHexString(typeNew));
        byte[] b = {2};
        byte[] amount = Marshaller.bigNumberMarshalBinary(this.amount);
        byte[] amountNew = Crypto.append(b,amount);
        logger.info("Amount Bytes: {}",Crypto.toHexString(amountNew));
//        byte[] amount = Marshaller.stringMarshaller(String.valueOf(this.amount));
        return Crypto.append(typeNew,amountNew);
    }
}
