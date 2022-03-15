package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.IssueTokensArg;

import java.math.BigInteger;

public class IssueTokens extends BasePayload {

    private final AccURL recipient;

    private final int amount;

    public IssueTokens(IssueTokensArg issueTokensArg) throws Exception {
        this.recipient = AccURL.toAccURL(issueTokensArg.getRecipient());
        this.amount = issueTokensArg.getAmount();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.IssueTokens.getValue())));
        byte[] recipientBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.recipient.string()));
        byte[] amountBytes = Crypto.append(Sequence.THREE,Marshaller.bigNumberMarshalBinary(BigInteger.valueOf(this.amount)));
        return Crypto.append(typeBytes,recipientBytes,amountBytes);
    }
}
