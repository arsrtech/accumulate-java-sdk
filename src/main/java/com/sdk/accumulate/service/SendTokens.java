package com.sdk.accumulate.service;

import com.sdk.accumulate.model.SendTokensArg;
import com.sdk.accumulate.model.TokenRecipientArg;

import java.util.ArrayList;
import java.util.List;

public class SendTokens extends BasePayload {

    private List<TokenRecipient> to;

    private byte[] hash;

    private byte[] meta;

    public SendTokens(SendTokensArg sendTokensArg) throws Exception {
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

    public List<TokenRecipient> getTo() {
        return to;
    }

    public void setTo(List<TokenRecipient> to) {
        this.to = to;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getMeta() {
        return meta;
    }

    public void setMeta(byte[] meta) {
        this.meta = meta;
    }

    @Override
    public byte[] _marshalBinary() {
        return new byte[0];
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
