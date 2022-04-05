package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;

import java.util.Arrays;

import static com.sdk.accumulate.service.AccURL.ACME_TOKEN_URL;
import static com.sdk.accumulate.service.Crypto.sha256;

public class LiteAccount extends KeypairSigner {

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
        byte[] hash = sha256(publicKey);
        byte[] copy = Arrays.copyOfRange(hash,0,20);
        String pkHash = Crypto.toHexString(copy);
        byte[] checkSum = sha256(pkHash.getBytes());
        byte[] checksumCopy = Arrays.copyOfRange(checkSum,28,32);
        String checkSumStr = Crypto.toHexString(checksumCopy);
        return AccURL.parse("acc://"+pkHash+checkSumStr+"/"+tokenUrl.authority());
    }

}
