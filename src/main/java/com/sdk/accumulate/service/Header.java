package com.sdk.accumulate.service;

import com.sdk.accumulate.model.HeaderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

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
        byte[] originBytes = this.origin.string().getBytes(StandardCharsets.UTF_8);
        logger.info("Header Origin: {}",Crypto.toHexString(originBytes));
        byte[] keyPageHeightBytes = BigInteger.valueOf(this.keyPageHeight).toByteArray();
        logger.info("keyPageHeight: {}",Crypto.toHexString(keyPageHeightBytes));
        byte[] keyPageIndexBytes = BigInteger.valueOf(this.keyPageIndex).toByteArray();
        logger.info("keyPageIndex: {}",Crypto.toHexString(keyPageIndexBytes));
        byte[] nonceBytes = BigInteger.valueOf(this.nonce).toByteArray();
        logger.info("Nonce: {}",this.nonce);
        return Crypto.append(originBytes,keyPageHeightBytes,keyPageIndexBytes,nonceBytes);
    }
}
