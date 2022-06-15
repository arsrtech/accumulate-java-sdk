package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.KeyPageOptions;

import java.util.Arrays;

import static com.sdk.accumulate.service.Crypto.sha256;

public class ADI extends KeypairSigner {

    /**
     * @param acmeTokenUrl
     * @param keypair
     */
    public ADI(AccURL acmeTokenUrl, TweetNaclFast.Signature.KeyPair keypair) {
        super(acmeTokenUrl.rootUrl(), keypair);
    }

    @Override
    public AccURL getSignerUrl() {
        return getUrl();
    }
}
