package com.sdk.accumulate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sdk.accumulate.model.AddCreditsArg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

@RestController
@RequestMapping("/test")
public class Test {

	private static final Logger logger = LoggerFactory.getLogger(Test.class);

	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();


	public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		// Static getInstance method is called with hashing SHA
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String toHexString(byte[] hash) {
		// Convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);

		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		// Pad with leading zeros
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}


	@GetMapping
	public ResponseEntity<?> test() throws Exception {
		
//		 KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
//         KeyPair kp = kpg.generateKeyPair();
//
//         PublicKey publicKey = kp.getPublic();
//         System.out.println("Test.main() public key "+publicKey.getEncoded());
//
//         String pkHash = toHexString(getSHA(publicKey.toString())).substring(0, 20);
//         System.out.println("Test.main() "+getSHA(publicKey.toString()));
//
//         String checkSum = toHexString(getSHA(pkHash.toString())).substring(28);
//         //System.out.println("Test.main() : "+checkSum);
//
//         String authority = toHexString(getSHA(pkHash.concat(checkSum)));
//
//         String url = "acc://"+pkHash+checkSum+"/ACME";
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(10);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCredits = accumulate.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCredits);
		logger.info("Response: "+response);
//		String liteAccountString = ow.writeValueAsString();
         return new ResponseEntity<>("", HttpStatus.ACCEPTED);
	}
}
