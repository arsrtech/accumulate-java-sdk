package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.AddCreditsArg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class AddCredits extends BasePayload{

    private static final Logger logger = LoggerFactory.getLogger(AddCredits.class);

    private final AccURL recipient;

    private final BigInteger amount;

    public AddCredits(AddCreditsArg addCreditsArg) throws Exception {
        super();
        this.recipient = AccURL.toAccURL(addCreditsArg.getRecipient());
        this.amount = addCreditsArg.getAmount();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Marshaller.integerMarshaller(TxType.AddCredits.getValue()));
        logger.info("Type : "+Crypto.toHexString(typeBytes));
        byte[] recipientBytes = Marshaller.stringMarshaller(this.recipient.string());
        logger.info("recipient: "+Crypto.toHexString(recipientBytes));
        byte[] amountBytes = Marshaller.uvarintMarshalBinary(this.amount);
        logger.info("Amount Bytes {}",amountBytes);
        logger.info("amount: "+Crypto.toHexString(amountBytes));
        return Crypto.append(typeBytes,recipientBytes,amountBytes);
    }
}

