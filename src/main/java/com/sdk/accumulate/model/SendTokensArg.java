package com.sdk.accumulate.model;

import java.util.List;

public class SendTokensArg {

    private List<TokenRecipientArg> to;

    private byte[] hash;

    private byte[] meta;

    public List<TokenRecipientArg> getTo() {
        return to;
    }

    public void setTo(List<TokenRecipientArg> to) {
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
}

