package com.sdk.accumulate.service;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    private static final Logger logger = LoggerFactory.getLogger(Crypto.class);

    public static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return  md.digest();
    }

    public static byte[] append(final byte[]... arrays) {
//        final ByteArrayOutputStream out = new ByteArrayOutputStream();
//        if (arrays != null) {
//            for (final byte[] array : arrays) {
//                if (array != null) {
//                    out.write(array, 0, array.length);
//                }
//            }
//        }
//        return out.toByteArray();
        byte[] copy = new byte[0];
        if (arrays != null) {
            for (final byte[] array : arrays) {
                if (array != null) {
                    copy = ArrayUtils.addAll(copy,array);
                }
            }
        }
        return copy;
    }

    public static String toHexString(byte[] bytes) {
//        BigInteger number = new BigInteger(1, hash);
//        return number.toString(16);
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }
}
