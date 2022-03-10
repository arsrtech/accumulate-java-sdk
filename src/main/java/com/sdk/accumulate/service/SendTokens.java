package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.SendTokensArg;
import com.sdk.accumulate.model.TokenRecipientArg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SendTokens extends BasePayload {

    private static final Logger logger = LoggerFactory.getLogger(SendTokens.class);

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
        byte[] typeBytes = Marshaller.integerMarshaller(3);
        byte[] a = {1};
        byte[] typeNew = Crypto.append(a,typeBytes);
        logger.info("Type Bytes: {}",Crypto.toHexString(typeNew));
//        validateHash(this.hash);
//        byte[] marshalBytes = Crypto.append(typeBytes,this.hash,this.meta);
        byte[] marshalBytes = Crypto.append(typeNew);
        validateRecipient(this.to);
        for (TokenRecipient tokenRecipient: this.to) {
            byte[] d = {4};
            byte[] recBytes = Crypto.append(d,Marshaller.bytesMarshaller(marshalTokenRecipient(tokenRecipient)));
            logger.info("Recipient Bytes: {}",Crypto.toHexString(recBytes));
            marshalBytes = Crypto.append(marshalBytes,recBytes);
        }
        return marshalBytes;
    }

    private byte[] marshalTokenRecipient(TokenRecipient tokenRecipient) {
        byte[] recipientBytes = Marshaller.stringMarshaller(tokenRecipient.getUrl().string());
        byte[] a = {1};
        recipientBytes = Crypto.append(a,recipientBytes);
        byte[] amountBytes = Marshaller.bigNumberMarshalBinary(BigInteger.valueOf(tokenRecipient.getAmount()));
        byte[] b = {2};
        amountBytes = Crypto.append(b,amountBytes);
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
