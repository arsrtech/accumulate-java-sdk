package com.sdk.accumulate.controller;

import com.sdk.accumulate.model.AccSignature;
import com.sdk.accumulate.model.KeyPageOptions;

import java.security.*;

public class KeypairSigner implements OriginSigner {

	protected AccURL origin;
	protected KeyPair keypair;
	protected int keyPageHeight;
	protected int keyPageIndex;
	
	KeypairSigner(AccURL origin, KeyPair keypair, KeyPageOptions keyPageOptions) {
		this.origin = origin;
		this.keypair = keypair;
		if (keyPageOptions.getKeyPageHeight() == 0)
			this.keyPageHeight = 1;
		else
			this.keyPageHeight = keyPageOptions.getKeyPageHeight();

		this.keyPageIndex = keyPageOptions.getKeyPageIndex();
	}

	public KeypairSigner(AccURL acmeTokenUrl, KeyPair keypair) {
		this.origin = acmeTokenUrl;
		this.keypair = keypair;
	}

	KeyPair keypair() {
		return this.keypair;
	}

	PublicKey publicKey() {
		return this.keypair.getPublic();
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
	AccSignature signRaw(byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		AccSignature accSignature = new AccSignature();
		accSignature.setPublicKey(keypair.getPublic().getEncoded());
		Signature sig = Signature.getInstance("SHA256withRSA");
		sig.initSign(keypair.getPrivate());
		sig.update(data);
		byte[] signatureBytes = sig.sign();
		accSignature.setSignature(signatureBytes);
		return accSignature;
	}
}
