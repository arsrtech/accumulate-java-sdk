package com.sdk.accumulate.controller;

import com.sdk.accumulate.model.AddCreditsArg;
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

	@GetMapping
	public ResponseEntity<?> test() throws Exception {
		Accumulate accumulate = new Accumulate("http://127.0.25.1:26660/v2");
		LiteAccount liteAccount = LiteAccount.generate();
		logger.info("Lite Account: {}",liteAccount.url().string());
		String response = accumulate.getFaucet(liteAccount.url().string());
		logger.info("Response: {}",response);
//		AddCreditsArg addCreditsArg = new AddCreditsArg();
//		addCreditsArg.setAmount(10);
//		addCreditsArg.setRecipient(liteAccount.url().string());
//		String addCredits = accumulate.addCredits(addCreditsArg,liteAccount);
//		logger.info("Add Credits Response {} ",addCredits);
         return new ResponseEntity<>("Create Account , Get Faucet, Add credits", HttpStatus.ACCEPTED);
	}
}
