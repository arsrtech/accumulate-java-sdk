package com.sdk.accumulate.controller;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.TxType;

import java.math.BigInteger;

public class AddCredits extends BasePayload{

    private final AccURL _recipient;

    private final BigInteger _amount;

    public AddCredits(AddCreditsArg addCreditsArg) throws Exception {
        super();
        this._recipient = AccURL.toAccURL(addCreditsArg.getRecipient());
        this._amount = BigInteger.valueOf(addCreditsArg.getAmount());
    }

    public AccURL get_recipient() {
        return _recipient;
    }

    public BigInteger get_amount() {
        return _amount;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] txnType = BigInteger.valueOf(TxType.AddCredits.getValue()).toByteArray();
        byte[] recipient = this._recipient.string().getBytes();
        byte[] amount = this._amount.toByteArray();
        byte[] marshalBytes = new byte[txnType.length+recipient.length+amount.length];
        System.arraycopy(txnType, 0, marshalBytes, 0, txnType.length);
        System.arraycopy(recipient, 0, marshalBytes, txnType.length, recipient.length);
        System.arraycopy(amount, 0, marshalBytes, txnType.length+recipient.length, amount.length);
        return marshalBytes;

    }
}

