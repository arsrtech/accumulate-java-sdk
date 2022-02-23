package com.sdk.accumulate.controller;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.BurnTokensArg;
import com.sdk.accumulate.model.CreateDataAccountArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.service.Accumulate;
import com.sdk.accumulate.service.LiteAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@GetMapping(value = "/add-credits")
	public ResponseEntity<?> addCredits() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);
		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(10);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCredits = accumulate.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCredits);
         return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/burn-tokens")
	public ResponseEntity<?> burnTokens() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		BurnTokensArg burnTokensArg = new BurnTokensArg();
		burnTokensArg.setAmount(1);
		String addCredits = accumulate.burnTokens(burnTokensArg,liteAccount);
		logger.info("Add Credits Response {} ",addCredits);
		return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-data-account")
	public ResponseEntity<?> createDataAccount() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateDataAccountArg createDataAccountArg = new CreateDataAccountArg();
		createDataAccountArg.setUrl(liteAccount.url().string());
		createDataAccountArg.setKeyBookUrl("");
		createDataAccountArg.setManagerKeyBookUrl("");
		createDataAccountArg.setScratch(true);
		String addCredits = accumulate.createDataAccount(createDataAccountArg,liteAccount);
		logger.info("Add Credits Response {} ",addCredits);
		return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-adi")
	public ResponseEntity<?> createIdentity() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl(liteAccount.url().string());
		createIdentityArg.setPublicKey(new byte[0]);
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String addCredits = accumulate.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create Identity Response {} ",addCredits);
		return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}
}
