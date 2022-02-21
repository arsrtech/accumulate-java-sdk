package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

import static com.sdk.accumulate.service.AccURL.ACME_TOKEN_URL;

public class LiteAccount extends KeypairSigner {

    private static final Logger logger = LoggerFactory.getLogger(LiteAccount.class);

    private AccURL tokenUrl;


    public LiteAccount(String acmeTokenUrl, TweetNaclFast.Signature.KeyPair keypair) throws Exception {
        super(computeUrl(keypair.getPublicKey(),AccURL.toAccURL(acmeTokenUrl)), keypair);
        this.tokenUrl = AccURL.toAccURL(tokenUrl.string());
    }

    public LiteAccount(AccURL acmeTokenUrl, TweetNaclFast.Signature.KeyPair keypair) throws Exception {
        super(computeUrl(keypair.getPublicKey(),acmeTokenUrl), keypair);
        this.tokenUrl = AccURL.toAccURL(acmeTokenUrl.string());
    }

    /**
     * Generate a new random LiteAccount for the ACME token
     */
    public static LiteAccount generate() throws Exception {
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        logger.info("Tweet Nacl pub Key {}",kp.getPublicKey());
        logger.info("ACME TOKEN URL: {}",AccURL.parse("acc://ACME").string());
        return new LiteAccount(AccURL.parse("acc://ACME"), kp);
    }

    /**
     * Generate a new LiteAccount for the ACME token with the given keypair
     */
    static LiteAccount generateWithKeypair(TweetNaclFast.Signature.KeyPair keypair) throws Exception {
        return new LiteAccount(ACME_TOKEN_URL, keypair);
    }

    /**
     * Generate a new random LiteAccount for the given token URL
     */
    static LiteAccount generateWithTokenUrl(AccURL tokenUrl) throws Exception {
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        return new LiteAccount(tokenUrl, kp);
    }

    static LiteAccount generateWithTokenUrl(String  tokenUrl) throws Exception {
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        return new LiteAccount(AccURL.toAccURL(tokenUrl), kp);
    }

    public AccURL url() {
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

    public static String toHexString(byte[] value) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, value);

        // Convert message digest into hex value

        // Pad with leading zeros
//        while (hexString.length() < 32) {
//            hexString.insert(0, '0');
//        }

        return number.toString(16);
    }

}
