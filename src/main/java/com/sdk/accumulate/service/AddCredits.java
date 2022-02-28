package com.sdk.accumulate.service;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.enums.TxnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

//        byte[] test = Marshaller.stringMarshaller("Hello 123");
//        logger.info("String test {} ",Crypto.toHexString(test));
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.AddCredits.getValue());
        logger.info("Type : "+Crypto.toHexString(typeBytes));
        byte[] recipientBytes = Marshaller.stringMarshaller(this._recipient.string());
        logger.info("recipient: "+Crypto.toHexString(recipientBytes));
        byte[] amountBytes = Marshaller.integerMarshaller(this._amount);
        logger.info("Amount Bytes {}",amountBytes);
        logger.info("amount: "+Crypto.toHexString(amountBytes));
        return Crypto.append(typeBytes,recipientBytes,amountBytes);
    }
}

