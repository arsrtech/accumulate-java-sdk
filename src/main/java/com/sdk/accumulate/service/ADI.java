package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.KeyPageOptions;

import java.util.Arrays;

import static com.sdk.accumulate.service.Crypto.sha256;

public class ADI extends KeypairSigner {

    ADI(AccURL origin, TweetNaclFast.Signature.KeyPair keypair, KeyPageOptions keyPageOptions) {
        super(origin, keypair, keyPageOptions);
    }

    public ADI(AccURL acmeTokenUrl, TweetNaclFast.Signature.KeyPair keypair) {
        super(acmeTokenUrl, keypair);
    }

    public static AccURL computeUrl(byte[] publicKey) throws Exception {
        byte[] hash = sha256(publicKey);
        byte[] copy = Arrays.copyOfRange(hash,0,20);
        String pkHash = Crypto.toHexString(copy);
        byte[] checkSum = sha256(pkHash.getBytes());
        byte[] checksumCopy = Arrays.copyOfRange(checkSum,28,32);
        String checkSumStr = Crypto.toHexString(checksumCopy);
        return AccURL.parse("acc://"+pkHash+checkSumStr);
    }
}
