# Accumulate Java SDK

## Summary
Accumulate SDK helps developers to develop the application with Accumulate network. It provides ready to use codebase with all Accumulate features. So they can create a new application with SDK or refactor the existing application.

## Prerequisite
- Java 15 or greater version

## How to use?
- Download the codebase and crate a java file inside src directory.
- Import and create a client.
- Then use all the client methods implemented in Java SDK.
### How to create the client
- The below client can connect with Accumulate public test net directly

``` Client client = new Client();```
- If you need to connect your local DevNet then pass your local URL in your client

```
Client client = new Client("http://127.0.25.1:26660/v2");
```

### How to use the client
- Client contains all Accumulate API features. You just need to call the method from client.
- The below code can generate a lite account and call accumulate network for GET Faucet
```
Client client = new Client();
LiteAccount liteAccount = LiteAccount.generate();
String response = client.getFaucet(liteAccount.url().string());
```
Note: In each client call you have to wait for few seconds to get the response from accumulate network

## Quick start tutorial
Demo of some main APIs of Accumulate:
```
package com.sdk.accumulate.controller;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.*;
import com.sdk.accumulate.service.*;
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
@RequestMapping("/my-app")
public class MyApp {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @GetMapping(value = "/create-account")
    public ResponseEntity<?> createAccount() throws Exception {
        Client client = new Client();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        logger.info("Lite Account Response: {}",response);
        Thread.sleep(5000);
        return new ResponseEntity<>("Create Account , Get Faucet", HttpStatus.ACCEPTED);
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
}

```

