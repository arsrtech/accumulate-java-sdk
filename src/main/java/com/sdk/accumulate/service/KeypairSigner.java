package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AccSignature;

import java.security.*;
import java.util.Arrays;


public abstract class KeypairSigner implements OriginSigner {

	protected final AccURL url;

	protected final TweetNaclFast.Signature.KeyPair keypair;

	protected int version;

	public KeypairSigner(AccURL signerUrl, TweetNaclFast.Signature.KeyPair keypair) {
		this.url = signerUrl;
		this.keypair = keypair;
		this.version = 1;
	}

	TweetNaclFast.Signature.KeyPair keypair() {
		return this.keypair;
	}

	public byte[] getPublicKey() {
		return this.keypair.getPublicKey();
	}

	public AccURL getUrl() {
		return this.url;
	}


    @Override
	public TweetNaclFast.Signature.KeyPair getKeypair() {
		return keypair;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public AccSignature sign(Transaction tx) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		return this.signRaw(tx.dataForSignature(signerInfo()));
	}

	public SignerInfo signerInfo() {
		SignerInfo signerInfo = new SignerInfo();
		signerInfo.setUrl(this.getSignerUrl().string());
		signerInfo.setVersion(this.version);
		signerInfo.setPublicKey(this.keypair.getPublicKey());
		signerInfo.setType(2);
		return signerInfo;
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
