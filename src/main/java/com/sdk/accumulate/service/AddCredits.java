package com.sdk.accumulate.service;

import com.sdk.accumulate.model.AddCreditsArg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class AddCredits extends BasePayload{

    private static final Logger logger = LoggerFactory.getLogger(AddCredits.class);

    private final AccURL _recipient;

    private final int _amount;

    public AddCredits(AddCreditsArg addCreditsArg) throws Exception {
        super();
        this._recipient = AccURL.toAccURL(addCreditsArg.getRecipient());
        this._amount = addCreditsArg.getAmount();
    }

    public AccURL get_recipient() {
        return _recipient;
    }

    public int get_amount() {
        return _amount;
    }

    @Override
    public byte[] _marshalBinary() {

        byte[] type = "addCredits".getBytes(StandardCharsets.UTF_8);
        byte[] recipient = this._recipient.string().getBytes(StandardCharsets.UTF_8);
        logger.info("recipient: "+Crypto.toHexString(recipient));
        byte[] amount = BigInteger.valueOf(this._amount).toByteArray();
        logger.info("amount: "+Crypto.toHexString(amount));
        return Crypto.append(type,recipient,amount);
    }
}

