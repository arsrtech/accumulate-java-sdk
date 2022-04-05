package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.AddCreditsArg;

import java.math.BigInteger;


public class AddCredits extends BasePayload{

    private final AccURL recipient;

    private final long amount;

    /**
     * @param addCreditsArg Add credits payload arguments
     * @throws Exception Throws Exception in case of Signer mismatch or Parsing Error
     */
    public AddCredits(AddCreditsArg addCreditsArg) throws Exception {
        super();
        this.recipient = AccURL.toAccURL(addCreditsArg.getRecipient());
        this.amount = addCreditsArg.getAmount();
    }

    /**
     * @return Payload Encoded Binary with parameter sequence number
     */
    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.AddCredits.getValue())));
        byte[] recipientBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.recipient.string()));
        byte[] amountBytes = Crypto.append(Sequence.THREE,Marshaller.longMarshaller(this.amount));
        return Crypto.append(typeBytes,recipientBytes,amountBytes);
    }
}

