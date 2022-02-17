package com.sdk.accumulate.controller;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

import org.json.simple.JSONObject;

public class KeyGenerator {
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
		KeyPair kp = kpg.generateKeyPair();
		
		PublicKey publicKey = kp.getPublic();
		byte[] encodedPublicKey = publicKey.getEncoded();
		String b64PublicKey = Base64.getEncoder().encodeToString(encodedPublicKey);
		
		JSONObject param = new JSONObject();
		param.put("url", "ADITEST");
		param.put("publicKey", b64PublicKey);
		param.put("keyBookName", "ADIKEYBOOK1");
		param.put("keyPageName", "ADIKEYPAGE1");
		
		JSONObject payload = new JSONObject();
		payload.put("payload", param);
		
		byte[] msg = payload.toJSONString().getBytes(StandardCharsets.UTF_8);

		Signature sig = Signature.getInstance("Ed25519");
		sig.initSign(kp.getPrivate());
		sig.update(msg);
		byte[] s = sig.sign();

		String encodedString = Base64.getEncoder().encodeToString(s);
		System.out.println(encodedString);
		
		
		System.out.println(b64PublicKey);
	}
}
