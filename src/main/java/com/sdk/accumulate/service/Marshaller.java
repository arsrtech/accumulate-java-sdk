package com.sdk.accumulate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class Marshaller {

    private static final Logger logger = LoggerFactory.getLogger(Marshaller.class);

    public static byte[] uvarintMarshalBinary(BigInteger number) {
        int i = 0;
        byte[] bytesArray = new byte[0];
        while (number.compareTo(BigInteger.valueOf(0x80)) >= 0) {
            logger.info("number Clear bit {}",number.clearBit(8).intValue());
//            bytesArray = Crypto.append(bytesArray,number.clearBit(8).toByteArray());
            number = number.shiftRight(7);
        }
        bytesArray = Crypto.append(bytesArray,Crypto.append(bytesArray,number.clearBit(8).toByteArray()));
        return bytesArray;
    }
}
