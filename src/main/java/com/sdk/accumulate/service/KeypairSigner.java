package com.sdk.accumulate.service;

//import com.iwebpp.crypto.TweetNaclFast;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AccSignature;
import com.sdk.accumulate.model.KeyPageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.util.Arrays;


public class KeypairSigner implements OriginSigner {

	private static final Logger logger = LoggerFactory.getLogger(KeypairSigner.class);

	protected AccURL origin;
	protected KeyPair keypair;
//	protected TweetNaclFast.Signature.KeyPair keypair;
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

	byte[] publicKey() {
		return this.keypair.getPublic().getEncoded();
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
//	AccSignature signRaw(byte[] data) {
//		logger.info("Data to sign {}",data);
////		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
//		AccSignature accSignature = new AccSignature();
//		byte[] pub = Arrays.copyOfRange(keypair.getSecretKey(),32,64);
//		logger.info("Private Key Len: {}",keypair.getSecretKey().length);
//		accSignature.setPublicKey(pub);
//		TweetNaclFast.Signature signature = new TweetNaclFast.Signature(keypair.getPublicKey(),keypair.getSecretKey());
//		byte[] signatureBytes = signature.detached(data);
//		accSignature.setSignature(signatureBytes);
//		return accSignature;
//	}
//
	AccSignature signRaw(byte[] data) {

		try {

			Signature sig = Signature.getInstance("Ed25519");
			sig.initSign(keypair.getPrivate());
			sig.update(data);
			byte[] s = sig.sign();

			byte[] pub = Arrays.copyOfRange(keypair.getPrivate().getEncoded(),16,48);
			AccSignature accSignature = new AccSignature();
			accSignature.setPublicKey(pub);
			accSignature.setSignature(s);
			return accSignature;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
