package com.sdk.accumulate.controller;

import com.sdk.accumulate.model.HeaderOptions;

import java.math.BigInteger;

public class Header {

    private AccURL origin;
    private long nonce;
    private long keyPageHeight;
    private long keyPageIndex;

    public Header(String origin, HeaderOptions headerOptions) throws Exception {
        this.origin = AccURL.toAccURL(origin);
        this.nonce = headerOptions.getNonce();
        this.keyPageHeight = headerOptions.getKeyPageHeight();
        this.keyPageIndex = headerOptions.getKeyPageIndex();
    }

    public AccURL getOrigin() {
        return origin;
    }

    public void setOrigin(AccURL origin) {
        this.origin = origin;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public long getKeyPageHeight() {
        return keyPageHeight;
    }

    public void setKeyPageHeight(long keyPageHeight) {
        this.keyPageHeight = keyPageHeight;
    }

    public long getKeyPageIndex() {
        return keyPageIndex;
    }

    public void setKeyPageIndex(long keyPageIndex) {
        this.keyPageIndex = keyPageIndex;
    }

    public byte[] marshalBinary() {
        byte[] originBytes = this.origin.string().getBytes();
        byte[] keyPageHeightBytes = BigInteger.valueOf(this.keyPageHeight).toByteArray();
        byte[] keyPageIndexBytes = BigInteger.valueOf(this.keyPageIndex).toByteArray();
        byte[] nonceBytes = BigInteger.valueOf(this.nonce).toByteArray();
        return Crypto.append(originBytes,keyPageHeightBytes,keyPageIndexBytes,nonceBytes);

    }
}

