package com.sdk.accumulate.controller;

import com.sdk.accumulate.model.*;
import com.sdk.accumulate.service.Accumulate;
import com.sdk.accumulate.service.LiteAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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
		String addCreditsResponse = accumulate.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
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
		String burnTokensResponse = accumulate.burnTokens(burnTokensArg,liteAccount);
		logger.info("Burn token Response {} ",burnTokensResponse);
		return new ResponseEntity<>("Create Account , Burn Tokens", HttpStatus.ACCEPTED);
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
		String createDataAccountResponse = accumulate.createDataAccount(createDataAccountArg,liteAccount);
		logger.info("Create data account Response {} ",createDataAccountResponse);
		return new ResponseEntity<>("Create Account , Create Data account", HttpStatus.ACCEPTED);
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
		String createAdiResponse = accumulate.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		return new ResponseEntity<>("Create Account , Create ADI", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-key-book")
	public ResponseEntity<?> createKeyBook() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateKeyBookArg createKeyBookArg = new CreateKeyBookArg();
		createKeyBookArg.setUrl(liteAccount.url().string());
		List<String> pages = new ArrayList<>();
		pages.add(liteAccount.url().string());
		createKeyBookArg.setPages(pages);
		String createKeyBook = accumulate.createKeyBook(createKeyBookArg,liteAccount);
		logger.info("Create Key book response Response {} ",createKeyBook);
		return new ResponseEntity<>("Create Account , Create Key book", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-key-page")
	public ResponseEntity<?> createKeyPage() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateKeyPageArg createKeyPageArg = new CreateKeyPageArg();
		List<byte[]> keys = new ArrayList<>();
		keys.add(new byte[0]);
		createKeyPageArg.setKeys(keys);
		createKeyPageArg.setUrl(liteAccount.url().string());
		String createKeyPageResponse = accumulate.createKeyPage(createKeyPageArg,liteAccount);
		logger.info("Create Key page response Response {} ",createKeyPageResponse);
		return new ResponseEntity<>("Create Account , create key page", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-token-account")
	public ResponseEntity<?> createTokenAccount() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateTokenAccountArg createTokenAccountArg = new CreateTokenAccountArg();
		createTokenAccountArg.setUrl(liteAccount.url().string());
		createTokenAccountArg.setTokenUrl(liteAccount.url().string());
		createTokenAccountArg.setKeyBookUrl(liteAccount.url().string());
		createTokenAccountArg.setScratch(true);
		String createTokenAccountResponse = accumulate.createTokenAccount(createTokenAccountArg,liteAccount);
		logger.info("Create Token Account response Response {} ",createTokenAccountResponse);
		return new ResponseEntity<>("Create Account , Create token account", HttpStatus.ACCEPTED);
	}
}
