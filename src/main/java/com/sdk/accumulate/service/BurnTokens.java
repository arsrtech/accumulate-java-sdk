package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.BurnTokensArg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class BurnTokens extends BasePayload{

    private static final Logger logger = LoggerFactory.getLogger(BurnTokens.class);

    private final BigInteger amount;

    public BurnTokens(BurnTokensArg burnTokensArg) {
        super();
        this.amount = burnTokensArg.getAmount();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateDataAccount.getValue())));
        byte[] amountBytes = Crypto.append(Sequence.TWO,Marshaller.bigNumberMarshalBinary(this.amount));
        return Crypto.append(typeBytes,amountBytes);
    }
}
