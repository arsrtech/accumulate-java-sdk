package com.sdk.accumulate.service;

import com.sdk.accumulate.model.BurnTokensArg;
import com.sdk.accumulate.enums.TxnType;

public class BurnTokens extends BasePayload{

    private int amount;

    public BurnTokens(BurnTokensArg burnTokensArg) {
        super();
        this.amount = burnTokensArg.getAmount();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] type = Marshaller.stringMarshaller(TxnType.BurnTokens.getValue());
        byte[] amount = Marshaller.integerMarshaller(this.amount);
        return Crypto.append(type,amount);
    }
}
