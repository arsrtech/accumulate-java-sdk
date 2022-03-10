package com.sdk.accumulate.service;

import com.sdk.accumulate.model.HeaderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Header {

    private static final Logger logger = LoggerFactory.getLogger(Header.class);

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
        byte[] originBytes = Marshaller.stringMarshaller(this.origin.string());
//        byte[] originBytes = Marshaller.stringMarshaller("acc://adi/foo");
        byte[] a = {1};
        byte[] originByteNew = Crypto.append(a,originBytes);
        logger.info("Header Origin: {}",Crypto.toHexString(originByteNew));
        byte[] keyPageHeightBytes = Marshaller.longMarshaller(this.keyPageHeight);
        byte[] b = {2};
        byte[] keyPageHeightNew = Crypto.append(b,keyPageHeightBytes);
        logger.info("keyPageHeight: {}",Crypto.toHexString(keyPageHeightNew));
        byte[] keyPageIndexBytes = Marshaller.longMarshaller(this.keyPageIndex);
        byte[] c = {3};
        byte[] keyPageIndexNew = Crypto.append(c,keyPageIndexBytes);
        logger.info("keyPageIndex: {}",Crypto.toHexString(keyPageIndexNew));
        byte[] nonceBytes = Marshaller.longMarshaller(this.nonce);
        byte[] d = {4};
        byte[] nonceNew = Crypto.append(d,nonceBytes);
        logger.info("Nonce: {}",Crypto.toHexString(nonceNew));
        return Crypto.append(originByteNew,keyPageHeightNew,nonceNew);
    }
}

