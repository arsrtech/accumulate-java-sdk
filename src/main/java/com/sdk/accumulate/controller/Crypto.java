package com.sdk.accumulate.controller;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    public static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return  md.digest();
    }

    public static byte[] append(final byte[]... arrays) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (arrays != null) {
            for (final byte[] array : arrays) {
                if (array != null) {
                    out.write(array, 0, array.length);
                }
            }
        }
        return out.toByteArray();
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        return number.toString(16);
    }
}
