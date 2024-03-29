package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AccSignature;
import com.sdk.accumulate.model.KeyPageOptions;

import java.security.*;
import java.util.Arrays;


public class KeypairSigner implements OriginSigner {

	protected AccURL origin;
	protected TweetNaclFast.Signature.KeyPair keypair;
	protected int keyPageHeight;
	protected int keyPageIndex;

	KeypairSigner(AccURL origin, TweetNaclFast.Signature.KeyPair keypair, KeyPageOptions keyPageOptions) {
		this.origin = origin;
		this.keypair = keypair;
		if (keyPageOptions.getKeyPageHeight() == 0)
			this.keyPageHeight = 1;
		else
			this.keyPageHeight = keyPageOptions.getKeyPageHeight();

		this.keyPageIndex = keyPageOptions.getKeyPageIndex();
	}

	public KeypairSigner(AccURL acmeTokenUrl, TweetNaclFast.Signature.KeyPair keypair) {
		this.origin = acmeTokenUrl;
		this.keypair = keypair;
	}

	TweetNaclFast.Signature.KeyPair keypair() {
		return this.keypair;
	}

	public byte[] getPublicKey() {
		return this.keypair.getPublicKey();
	}

	@Override
	public AccURL getOrigin() {
		return this.origin;
	}

	@Override
	public int getKeyPageHeight() {
		return this.keyPageHeight;
	}

	@Override
	public int getKeyPageIndex() {
		return this.keyPageIndex;
	}

	String string() {
		return this.origin.string();
	}

	public AccSignature sign(Transaction tx) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		return this.signRaw(tx.dataForSignature());
	}

	/**
	 * Sign arbitrary data.
	 */
	AccSignature signRaw(byte[] data) {
		AccSignature accSignature = new AccSignature();
		byte[] pub = Arrays.copyOfRange(keypair.getSecretKey(),32,64);
		accSignature.setPublicKey(pub);
		TweetNaclFast.Signature signature = new TweetNaclFast.Signature(keypair.getPublicKey(),keypair.getSecretKey());
		byte[] signatureBytes = signature.detached(data);
		accSignature.setSignature(signatureBytes);
		return accSignature;
	}
}
