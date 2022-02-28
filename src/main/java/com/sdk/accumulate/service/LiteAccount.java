package com.sdk.accumulate.service;

//import com.iwebpp.crypto.TweetNaclFast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.util.Arrays;

import static com.sdk.accumulate.service.AccURL.ACME_TOKEN_URL;

public class LiteAccount extends KeypairSigner {

    private static final Logger logger = LoggerFactory.getLogger(LiteAccount.class);

    private AccURL tokenUrl;




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
    public static LiteAccount generate() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();
        logger.info("Tweet Nacl pub Key {}",kp.getPublic());
        logger.info("ACME TOKEN URL: {}",AccURL.parse("acc://ACME").string());
        return new LiteAccount(AccURL.parse("acc://ACME"), kp);
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
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();
        return new LiteAccount(tokenUrl, kp);
    }

    static LiteAccount generateWithTokenUrl(String  tokenUrl) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        KeyPair kp = kpg.generateKeyPair();
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
        logger.info("Hash: {}",Crypto.toHexString(hash));
        byte[] copy = Arrays.copyOfRange(hash,0,20);
        String pkHash = Crypto.toHexString(copy);
        logger.info("PK Hash: {} ",pkHash);
        byte[] checkSum = md1.digest(pkHash.getBytes());
        byte[] checksumCopy = Arrays.copyOfRange(checkSum,28,32);
        String checkSumStr = Crypto.toHexString(checksumCopy);
        logger.info("CheckSum Str: {}",checkSumStr);
        return AccURL.parse("acc://"+pkHash+checkSumStr+"/"+tokenUrl.authority());
    }

}
