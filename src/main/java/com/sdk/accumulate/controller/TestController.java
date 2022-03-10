package com.sdk.accumulate.controller;

import com.sdk.accumulate.enums.KeyPageOperation;
import com.sdk.accumulate.model.*;
import com.sdk.accumulate.service.Client;
import com.sdk.accumulate.service.LiteAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@GetMapping(value = "/add-credits")
	public ResponseEntity<?> addCredits() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);
		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(BigInteger.valueOf(10));
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
         return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/burn-tokens")
	public ResponseEntity<?> burnTokens() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		Thread.sleep(5000);
		logger.info("Response: {}",response);

		BurnTokensArg burnTokensArg = new BurnTokensArg();
		burnTokensArg.setAmount(BigInteger.valueOf(1));
		String burnTokensResponse = client.burnTokens(burnTokensArg,liteAccount);
		logger.info("Burn token Response {} ",burnTokensResponse);
		return new ResponseEntity<>("Create Account , Burn Tokens", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-data-account")
	public ResponseEntity<?> createDataAccount() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateDataAccountArg createDataAccountArg = new CreateDataAccountArg();
		createDataAccountArg.setUrl(liteAccount.url().string());
		createDataAccountArg.setKeyBookUrl("");
		createDataAccountArg.setManagerKeyBookUrl("");
		createDataAccountArg.setScratch(true);
		String createDataAccountResponse = client.createDataAccount(createDataAccountArg,liteAccount);
		logger.info("Create data account Response {} ",createDataAccountResponse);
		return new ResponseEntity<>("Create Account , Create Data account", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-adi")
	public ResponseEntity<?> createIdentity() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl(liteAccount.url().string());
		createIdentityArg.setPublicKey(new byte[0]);
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		return new ResponseEntity<>("Create Account , Create ADI", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-key-book")
	public ResponseEntity<?> createKeyBook() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateKeyBookArg createKeyBookArg = new CreateKeyBookArg();
		createKeyBookArg.setUrl(liteAccount.url().string());
		List<String> pages = new ArrayList<>();
		pages.add(liteAccount.url().string());
		createKeyBookArg.setPages(pages);
		String createKeyBook = client.createKeyBook(createKeyBookArg,liteAccount);
		logger.info("Create Key book Response {} ",createKeyBook);
		return new ResponseEntity<>("Create Account , Create Key book", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-key-page")
	public ResponseEntity<?> createKeyPage() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateKeyPageArg createKeyPageArg = new CreateKeyPageArg();
		List<byte[]> keys = new ArrayList<>();
		keys.add(new byte[0]);
		createKeyPageArg.setKeys(keys);
		createKeyPageArg.setUrl(liteAccount.url().string());
		String createKeyPageResponse = client.createKeyPage(createKeyPageArg,liteAccount);
		logger.info("Create Key page Response {} ",createKeyPageResponse);
		return new ResponseEntity<>("Create Account , create key page", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-token-account")
	public ResponseEntity<?> createTokenAccount() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateTokenAccountArg createTokenAccountArg = new CreateTokenAccountArg();
		createTokenAccountArg.setUrl(liteAccount.url().string());
		createTokenAccountArg.setTokenUrl(liteAccount.url().string());
		createTokenAccountArg.setKeyBookUrl(liteAccount.url().string());
		createTokenAccountArg.setScratch(true);
		String createTokenAccountResponse = client.createTokenAccount(createTokenAccountArg,liteAccount);
		logger.info("Create Token Account Response {} ",createTokenAccountResponse);
		return new ResponseEntity<>("Create Account , Create token account", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-token")
	public ResponseEntity<?> createToken() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		CreateTokenArg createTokenArg = new CreateTokenArg();
		createTokenArg.setUrl(liteAccount.url().string());
		createTokenArg.setKeyBookUrl("url");
		createTokenArg.setSymbol("INR");
		createTokenArg.setPrecision(10);
		createTokenArg.setProperties("Property");
		createTokenArg.setInitialSupply(1000000000);
		createTokenArg.setHasSupplyLimit(true);
		String createTokenResponse = client.createToken(createTokenArg,liteAccount);
		logger.info("Create Token Response {} ",createTokenResponse);
		return new ResponseEntity<>("Create Account , Create token", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/issue-token")
	public ResponseEntity<?> issueToken() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		IssueTokensArg issueTokensArg = new IssueTokensArg();
		issueTokensArg.setRecipient(liteAccount.url().string());
		issueTokensArg.setAmount(10);
		String issueTokensResponse = client.issueTokens(issueTokensArg,liteAccount);
		logger.info("Issue Token  Response {} ",issueTokensResponse);
		return new ResponseEntity<>("Create Account , Issue token", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/send-token")
	public ResponseEntity<?> sendToken() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		SendTokensArg sendTokensArg = new SendTokensArg();
		sendTokensArg.setHash(new byte[0]);
		sendTokensArg.setMeta(new byte[0]);
		List<TokenRecipientArg> to = new ArrayList<>();
		TokenRecipientArg tokenRecipientArg = new TokenRecipientArg();
		tokenRecipientArg.setAmount(10);
		tokenRecipientArg.setUrl("acc://bob/tokens");
		TokenRecipientArg tokenRecipientArg2 = new TokenRecipientArg();
		tokenRecipientArg2.setAmount(25);
		tokenRecipientArg2.setUrl("acc://charlie/tokens");
		to.add(tokenRecipientArg);
		to.add(tokenRecipientArg2);
		sendTokensArg.setTo(to);
		String sendTokensResponse = client.sendToken(sendTokensArg,liteAccount);
		logger.info("Issue Token  Response {} ",sendTokensResponse);
		return new ResponseEntity<>("Create Account , Send token ", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/update-key-page")
	public ResponseEntity<?> updateKeyPage() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		UpdateKeyPageArg updateKeyPageArg = new UpdateKeyPageArg();
		updateKeyPageArg.setOwner(liteAccount.url().string());
		updateKeyPageArg.setKey(new byte[0]);
		updateKeyPageArg.setOperation(KeyPageOperation.AddKey);
		updateKeyPageArg.setThreshold(1);
		String updateKeyPageResponse = client.updateKeyPage(updateKeyPageArg,liteAccount);
		logger.info("update KeyPage Response  Response {} ",updateKeyPageResponse);
		return new ResponseEntity<>("Create Account , update KeyPage ", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/write-data")
	public ResponseEntity<?> writeData() throws Exception {
		Client client = new Client("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);

		WriteDataArg writeDataArg = new WriteDataArg();
		writeDataArg.setData(new byte[0]);
		List<byte[]> bytes = new ArrayList<>();
		bytes.add(new byte[0]);
		writeDataArg.setExtIds(bytes);
		String writeDataResponse = client.writeData(writeDataArg,liteAccount);
		logger.info("Write data Response  Response {} ",writeDataResponse);
		return new ResponseEntity<>("Create Account , Write data ", HttpStatus.ACCEPTED);
	}
}
