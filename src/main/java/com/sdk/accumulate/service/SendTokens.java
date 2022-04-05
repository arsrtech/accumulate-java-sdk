package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.SendTokensArg;
import com.sdk.accumulate.model.TokenRecipientArg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SendTokens extends BasePayload {

    private final List<TokenRecipient> to;

    private byte[] hash;

    private byte[] meta;

    public SendTokens(SendTokensArg sendTokensArg) throws Exception {
        super();
        List<TokenRecipient> tokenRecipients = new ArrayList<>();
        for (TokenRecipientArg tokenRecipientArg: sendTokensArg.getTo()) {
            TokenRecipient tokenRecipient = new TokenRecipient();
            tokenRecipient.setUrl(AccURL.toAccURL(tokenRecipientArg.getUrl()));
            tokenRecipient.setAmount(tokenRecipientArg.getAmount());
            tokenRecipients.add(tokenRecipient);
        }
        this.to = tokenRecipients;
        this.hash = sendTokensArg.getHash();
        this.meta = sendTokensArg.getMeta();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.SendTokens.getValue())));
        validateRecipient(this.to);
        byte[] recipientBytes = new byte[0];
        for (TokenRecipient tokenRecipient: this.to) {
            recipientBytes = Crypto.append(recipientBytes,Sequence.FOUR,Marshaller.bytesMarshaller(marshalTokenRecipient(tokenRecipient)));
        }
        return Crypto.append(typeBytes,recipientBytes);
    }

    private byte[] marshalTokenRecipient(TokenRecipient tokenRecipient) {
        byte[] recipientBytes = Crypto.append(Sequence.ONE,Marshaller.stringMarshaller(tokenRecipient.getUrl().string()));
        byte[] amountBytes = Crypto.append(Sequence.TWO,Marshaller.bigNumberMarshalBinary(BigInteger.valueOf(tokenRecipient.getAmount())));
        return Crypto.append(recipientBytes,amountBytes);
    }

    public void validateHash(byte[] hash) {
        if (hash.length != 32)
            throw new Error("Invalid hash");
    }

    public void validateRecipient(List<TokenRecipient> tokenRecipients) {
        if (tokenRecipients.size() < 1)
            throw new Error("Missing at least one recipient");
    }
}

class TokenRecipient {

    private AccURL url;

    private int amount;

    public AccURL getUrl() {
        return url;
    }

    public void setUrl(AccURL url) {
        this.url = url;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
