package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.IssueTokensArg;

public class IssueTokens extends BasePayload {

    private AccURL recipient;

    private int amount;

    public IssueTokens(IssueTokensArg issueTokensArg) throws Exception {
        this.recipient = AccURL.toAccURL(issueTokensArg.getRecipient());
        this.amount = issueTokensArg.getAmount();
    }

    public AccURL getRecipient() {
        return recipient;
    }

    public void setRecipient(AccURL recipient) {
        this.recipient = recipient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.IssueToken.getValue());
        byte[] recipientBytes = Marshaller.stringMarshaller(this.recipient.string());
        byte[] amountBytes = Marshaller.integerMarshaller(this.amount);
        return Crypto.append(typeBytes,recipientBytes,amountBytes);
    }
}
