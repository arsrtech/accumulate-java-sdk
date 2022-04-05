package com.sdk.accumulate.service;

import org.apache.commons.lang3.ArrayUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    public static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return  md.digest(data);
    }

    public static byte[] append(final byte[]... arrays) {
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
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            result.append(String.format("%02X", temp));
        }
        return result.toString();
    }
}
