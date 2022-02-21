package com.sdk.accumulate.service;


import com.sdk.accumulate.model.AccSignature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface OriginSigner {

	AccURL getOrigin();

	int getKeyPageHeight();

	int getKeyPageIndex();

	AccSignature sign(Transaction transaction) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException;
}

