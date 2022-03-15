package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.model.HeaderOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Header {

    private static final Logger logger = LoggerFactory.getLogger(Header.class);

    private final AccURL origin;

    private final long nonce;

    private final long keyPageHeight;

    private final long keyPageIndex;

    public Header(String origin, HeaderOptions headerOptions) throws Exception {
        super();
        this.origin = AccURL.toAccURL(origin);
        this.nonce = headerOptions.getNonce();
        this.keyPageHeight = headerOptions.getKeyPageHeight();
        this.keyPageIndex = headerOptions.getKeyPageIndex();
    }

    public AccURL getOrigin() {
        return origin;
    }

    public long getNonce() {
        return nonce;
    }

    public long getKeyPageHeight() {
        return keyPageHeight;
    }

    public long getKeyPageIndex() {
        return keyPageIndex;
    }

    public byte[] marshalBinary() {
        byte[] originBytes = Crypto.append(Sequence.ONE,Marshaller.stringMarshaller(this.origin.string()));
        byte[] keyPageHeightBytes = Crypto.append(Sequence.TWO,Marshaller.longMarshaller(this.keyPageHeight));
        byte[] keyPageIndexBytes = Crypto.append(Sequence.THREE,Marshaller.longMarshaller(this.keyPageIndex));
        byte[] nonceBytes = Crypto.append(Sequence.FOUR,Marshaller.longMarshaller(this.nonce));
        return Crypto.append(originBytes,keyPageHeightBytes,nonceBytes);
    }
}

