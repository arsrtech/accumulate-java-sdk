package io.accumulatenetwork.sdk.protocol;


import io.accumulatenetwork.sdk.commons.codec.binary.Hex;
import io.accumulatenetwork.sdk.generated.protocol.LiteTokenAccount;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.signing.AccKeyPairGenerator;

import java.util.Arrays;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;

public class LiteAccount extends Principal {

    private static final UrlRegistry urlRegistry = new UrlRegistry();


    public LiteAccount(SignatureKeyPair keyPair) {
        super(new LiteTokenAccount()
                .tokenUrl(urlRegistry.getAcmeTokenUrl())
                .url(computeUrl(keyPair.getPublicKey(), urlRegistry.getAcmeTokenUrl())), keyPair);
    }

    public LiteAccount(Url acmeTokenUrl, SignatureKeyPair keyPair) {
        super(new LiteTokenAccount().tokenUrl(Url.toAccURL(acmeTokenUrl.string())), keyPair);
    }

    /**
     * Generate a new random LiteAccount for the ACME token
     */
    public static LiteAccount generate(final SignatureType signatureType) {
        final SignatureKeyPair keyPair = new SignatureKeyPair(AccKeyPairGenerator.generate(signatureType), signatureType);
        return new LiteAccount(keyPair);
    }

    /**
     * Generate a new LiteAccount for the ACME token with the given keypair
     */
    static LiteAccount generateWithKeypair(final SignatureKeyPair signatureKeyPair) {
        return new LiteAccount(urlRegistry.getAcmeTokenUrl(), signatureKeyPair);
    }

    /**
     * Generate a new random LiteAccount for the given token URL
     */
    static LiteAccount generateWithTokenUrl(final Url tokenUrl, final SignatureType signatureType) {
        final SignatureKeyPair keyPair = new SignatureKeyPair(AccKeyPairGenerator.generate(signatureType), signatureType);
        return new LiteAccount(tokenUrl, keyPair);
    }

    static LiteAccount generateWithTokenUrl(final String tokenUrl, final SignatureType signatureType) {
        return generateWithTokenUrl(Url.toAccURL(tokenUrl), signatureType);
    }

    static Url computeUrl(final byte[] publicKey, final Url tokenUrl) {
        final byte[] hash = sha256(publicKey);
        final byte[] copy = Arrays.copyOfRange(hash, 0, 20);
        final String pkHash = Hex.encodeHexString(copy);
        final byte[] checkSum = sha256(pkHash.getBytes());
        final byte[] checksumCopy = Arrays.copyOfRange(checkSum, 28, 32);
        final String checkSumStr = Hex.encodeHexString(checksumCopy);
        return Url.parse("acc://" + pkHash + checkSumStr + "/" + tokenUrl.authority());
    }
}
