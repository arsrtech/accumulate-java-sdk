package com.sdk.accumulate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.sdk.accumulate.controller.AccURL.ACME_TOKEN_URL;

public class LiteAccount extends KeypairSigner {

    private static final Logger logger = LoggerFactory.getLogger(LiteAccount.class);

    private AccURL tokenUrl;

    static KeyPairGenerator kpg;

    static {
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public LiteAccount(String acmeTokenUrl, KeyPair keypair) throws Exception {
        super(computeUrl(keypair.getPublic().getEncoded(),AccURL.toAccURL(acmeTokenUrl)), keypair);
        this.tokenUrl = AccURL.toAccURL(tokenUrl.string());
    }

    public LiteAccount(AccURL acmeTokenUrl, KeyPair keypair) throws Exception {
        super(computeUrl(keypair.getPublic().getEncoded(),acmeTokenUrl), keypair);
        this.tokenUrl = AccURL.toAccURL(acmeTokenUrl.string());
    }

    /**
     * Generate a new random LiteAccount for the ACME token
     */
    static LiteAccount generate() throws Exception {
        KeyPair keyPair = kpg.generateKeyPair();
        logger.info("KeyPair: {}",keyPair);
        logger.info("ACME TOKEN URL: {}",AccURL.parse("acc://ACME").string());
        return new LiteAccount(AccURL.parse("acc://ACME"), keyPair);
    }

    /**
     * Generate a new LiteAccount for the ACME token with the given keypair
     */
    static LiteAccount generateWithKeypair(KeyPair keypair) throws Exception {
        return new LiteAccount(ACME_TOKEN_URL, keypair);
    }

    /**
     * Generate a new random LiteAccount for the given token URL
     */
    static LiteAccount generateWithTokenUrl(AccURL tokenUrl) throws Exception {
        return new LiteAccount(tokenUrl, kpg.generateKeyPair());
    }

    static LiteAccount generateWithTokenUrl(String  tokenUrl) throws Exception {
        return new LiteAccount(AccURL.toAccURL(tokenUrl), kpg.generateKeyPair());
    }

    AccURL url() {
        return this.origin;
    }

    AccURL tokenUrl() {
        return this.tokenUrl;
    }

    static AccURL computeUrl(byte[] publicKey, AccURL tokenUrl) throws Exception {
        logger.info("Computing URL");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        MessageDigest md1 = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(publicKey);
        logger.info("Hash: {}",toHexString(hash));
        byte[] copy = Arrays.copyOfRange(hash,0,20);
        String pkHash = toHexString(copy);
        logger.info("PK Hash: {} ",pkHash);
        byte[] checkSum = md1.digest(pkHash.getBytes());
        byte[] checksumCopy = Arrays.copyOfRange(checkSum,28,32);
        String checkSumStr = toHexString(checksumCopy);
        logger.info("CheckSum Str: {}",checkSumStr);
        return AccURL.parse("acc://"+pkHash+checkSumStr+"/"+tokenUrl.authority());
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
//        while (hexString.length() < 32) {
//            hexString.insert(0, '0');
//        }

        return hexString.toString();
    }

}
