package com.sdk.accumulate.controller;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.enums.KeyPageOperation;
import com.sdk.accumulate.model.*;
import com.sdk.accumulate.service.ADI;
import com.sdk.accumulate.service.AccURL;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	private static final String baseUrl = "http://127.0.25.1:26660/v2";

	@GetMapping(value = "/add-credits")
	public ResponseEntity<?> addCredits() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(1000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
         return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/burn-tokens")
	public ResponseEntity<?> burnTokens() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		BurnTokensArg burnTokensArg = new BurnTokensArg();
		burnTokensArg.setAmount(BigInteger.valueOf(1));
		String burnTokensResponse = client.burnTokens(burnTokensArg,liteAccount);
		logger.info("Burn token Response {} ",burnTokensResponse);
		return new ResponseEntity<>("Create Account , Burn Tokens", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-data-account")
	public ResponseEntity<?> createDataAccount() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		CreateDataAccountArg createDataAccountArg = new CreateDataAccountArg();
		createDataAccountArg.setUrl(liteAccount.url().string()+"/data");
		createDataAccountArg.setKeyBookUrl("acc://pun1/pun1book");
		createDataAccountArg.setManagerKeyBookUrl("acc://pun1/pun1book");
		createDataAccountArg.setScratch(true);
		String createDataAccountResponse = client.createDataAccount(createDataAccountArg,liteAccount);
		logger.info("Create data account Response {} ",createDataAccountResponse);
		return new ResponseEntity<>("Create Account , Create Data account", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-adi")
	public ResponseEntity<?> createIdentity() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl("acc://my-own-identity");
		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
		createIdentityArg.setPublicKey(kp.getPublicKey());
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		return new ResponseEntity<>("Create Account , Create ADI", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-key-book")
	public ResponseEntity<?> createKeyBook() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		String identityUrl = "acc://my-own-identity-1";
		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl(identityUrl);
		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
		createIdentityArg.setPublicKey(kp.getPublicKey());
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		Thread.sleep(5000);
		ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

		CreateKeyBookArg createKeyBookArg = new CreateKeyBookArg();
		createKeyBookArg.setUrl(identityUrl+"/book1");
		List<String> pages = new ArrayList<>();
		pages.add(identityUrl+"/test-key-page");
		createKeyBookArg.setPages(pages);
		String createKeyBook = client.createKeyBook(createKeyBookArg,adi);
		logger.info("Create Key book Response {} ",createKeyBook);
		return new ResponseEntity<>("Create Account , Create Key book", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-key-page")
	public ResponseEntity<?> createKeyPage() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		String identityUrl = "acc://my-own-identity-1";
		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl(identityUrl);
		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
		createIdentityArg.setPublicKey(kp.getPublicKey());
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		Thread.sleep(5000);
		ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

		AddCreditsArg addCreditsArg2 = new AddCreditsArg();
		addCreditsArg2.setAmount(500000);
		addCreditsArg2.setRecipient(identityUrl);
		String addCreditsResponse2 = client.addCredits(addCreditsArg2,adi);
		logger.info("Add Credits Response2 {} ",addCreditsResponse2);
		Thread.sleep(5000);

		CreateKeyPageArg createKeyPageArg = new CreateKeyPageArg();
		List<byte[]> keys = new ArrayList<>();
		keys.add(liteAccount.getPublicKey());
		createKeyPageArg.setKeys(keys);
		createKeyPageArg.setUrl(identityUrl+"/test-key-page");
		String createKeyPageResponse = client.createKeyPage(createKeyPageArg,adi);
		logger.info("Create Key page Response {} ",createKeyPageResponse);
		return new ResponseEntity<>("Create Account , create key page", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-token-account")
	public ResponseEntity<?> createTokenAccount() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		String identityUrl = "acc://my-own-identity-2";
		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl(identityUrl);
		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
		createIdentityArg.setPublicKey(kp.getPublicKey());
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		Thread.sleep(5000);
		ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

		CreateTokenAccountArg createTokenAccountArg = new CreateTokenAccountArg();
		createTokenAccountArg.setUrl(identityUrl);
		createTokenAccountArg.setTokenUrl("acc://TEST");
		createTokenAccountArg.setKeyBookUrl("acc://my-own-identity-2/test-key-page");
		createTokenAccountArg.setScratch(true);
		String createTokenAccountResponse = client.createTokenAccount(createTokenAccountArg,adi);
		logger.info("Create Token Account Response {} ",createTokenAccountResponse);
		return new ResponseEntity<>("Create Account , Create token account", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/create-token")
	public ResponseEntity<?> createToken() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		String identityUrl = "acc://my-own-identity-3";
		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl(identityUrl);
		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
		createIdentityArg.setPublicKey(kp.getPublicKey());
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		Thread.sleep(5000);
		ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

		CreateTokenArg createTokenArg = new CreateTokenArg();
		createTokenArg.setUrl(identityUrl);
		createTokenArg.setKeyBookUrl("acc://my-own-identity-3/test-key-page");
		createTokenArg.setSymbol("INR");
		createTokenArg.setPrecision(10);
		createTokenArg.setProperties("acc://my-own-identity-3/INR");
		createTokenArg.setInitialSupply(BigInteger.valueOf(1000000000));
		createTokenArg.setHasSupplyLimit(true);
		createTokenArg.setManager("acc://my-own-identity-3/test-key-page");
		String createTokenResponse = client.createToken(createTokenArg,adi);
		logger.info("Create Token Response {} ",createTokenResponse);
		return new ResponseEntity<>("Create Account , Create token", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/issue-token")
	public ResponseEntity<?> issueToken() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		LiteAccount liteAccount2 = LiteAccount.generate();
		String response2 = client.getFaucet(liteAccount2.url().string());
		logger.info("Lite Account Response: {}",response2);
		Thread.sleep(5000);

		IssueTokensArg issueTokensArg = new IssueTokensArg();
		issueTokensArg.setRecipient(liteAccount2.url().string());
		issueTokensArg.setAmount(1);
		String issueTokensResponse = client.issueTokens(issueTokensArg,liteAccount);
		logger.info("Issue Token  Response {} ",issueTokensResponse);
		return new ResponseEntity<>("Create Account , Issue token", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/send-token")
	public ResponseEntity<?> sendToken() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		LiteAccount liteAccount2 = LiteAccount.generate();
		String response2 = client.getFaucet(liteAccount2.url().string());
		logger.info("Lite Account Response: {}",response2);
		Thread.sleep(5000);

		SendTokensArg sendTokensArg = new SendTokensArg();
//		sendTokensArg.setHash(new byte[0]);
//		sendTokensArg.setMeta(new byte[0]);
		List<TokenRecipientArg> to = new ArrayList<>();
		TokenRecipientArg tokenRecipientArg = new TokenRecipientArg();
		tokenRecipientArg.setAmount(20000000);
		tokenRecipientArg.setUrl(liteAccount2.url().string());
		to.add(tokenRecipientArg);
		sendTokensArg.setTo(to);
		String sendTokensResponse = client.sendToken(sendTokensArg,liteAccount);
		logger.info("Send Token  Response {} ",sendTokensResponse);
		return new ResponseEntity<>("Create Account , Send token ", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/update-key-page")
	public ResponseEntity<?> updateKeyPage() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);


		AddCreditsArg addCreditsArg = new AddCreditsArg();
		addCreditsArg.setAmount(500000);
		addCreditsArg.setRecipient(liteAccount.url().string());
		String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
		logger.info("Add Credits Response {} ",addCreditsResponse);
		Thread.sleep(5000);

		CreateIdentityArg createIdentityArg = new CreateIdentityArg();
		createIdentityArg.setUrl("acc://my-own-identity");
		TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
		createIdentityArg.setPublicKey(kp.getPublicKey());
		createIdentityArg.setKeyBookName("test-key-book");
		createIdentityArg.setKeyPageName("test-key-page");
		String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
		logger.info("Create ADI Response {} ",createAdiResponse);
		Thread.sleep(5000);

		UpdateKeyPageArg updateKeyPageArg = new UpdateKeyPageArg();
		updateKeyPageArg.setOwner(liteAccount.url().string());
		TweetNaclFast.Signature.KeyPair kp1 = TweetNaclFast.Signature.keyPair();
		updateKeyPageArg.setNewKey(kp1.getPublicKey());
		updateKeyPageArg.setOperation(KeyPageOperation.AddKey);
//		updateKeyPageArg.setThreshold(1);
		String updateKeyPageResponse = client.updateKeyPage(updateKeyPageArg,liteAccount);
		logger.info("update KeyPage Response  Response {} ",updateKeyPageResponse);
		return new ResponseEntity<>("Create Account , update KeyPage ", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/write-data")
	public ResponseEntity<?> writeData() throws Exception {
		Client client = new Client(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);

		WriteDataArg writeDataArg = new WriteDataArg();
		writeDataArg.setData("Test".getBytes(StandardCharsets.UTF_8));
		List<byte[]> bytes = new ArrayList<>();
		bytes.add(new byte[0]);
		writeDataArg.setExtIds(bytes);
		String writeDataResponse = client.writeData(writeDataArg,liteAccount);
		logger.info("Write data Response  Response {} ",writeDataResponse);
		return new ResponseEntity<>("Create Account , Write data ", HttpStatus.ACCEPTED);
	}
}
