package com.sdk.accumulate.model;

public class HeaderOptions {
    private long nonce;
    private long keyPageHeight;
    private long keyPageIndex;

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
}
