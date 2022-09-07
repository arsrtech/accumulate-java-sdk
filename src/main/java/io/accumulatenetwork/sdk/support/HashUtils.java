package io.accumulatenetwork.sdk.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static final MessageDigest messageDigest256;

    private static final MessageDigest messageDigest512;

    static {
        try {
            messageDigest256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            messageDigest512 = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha256(final byte[] data) {
        return messageDigest256.digest(data);
    }

    public static byte[] sha512(final byte[] data) {
        return messageDigest512.digest(data);
    }
}
