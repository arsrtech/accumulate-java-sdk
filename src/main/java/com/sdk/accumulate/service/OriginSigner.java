package com.sdk.accumulate.service;


import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AccSignature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface OriginSigner {

	AccURL getSignerUrl();

	TweetNaclFast.Signature.KeyPair getKeypair();

	int getVersion();

	AccSignature sign(Transaction transaction) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException;
}

