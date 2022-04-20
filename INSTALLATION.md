# Accumulate Java SDK installation Guide

## Introduction:
Accumulate Java SDK helps the developers to integrate their applications with Accumulate network. It has ready-made methods to execute. So the developers can reuse the codebase and develop their application efficiently.

## Requirements
- Java 11 or greater version

## Install Java
- To install Open Jdk please follow the below steps
    - `$ sudo apt update`
    - `$ sudo apt install default-jre`
    - `$ java -version`
      - Output : \
        `openjdk version "11.0.11" 2021-04-20
        OpenJDK Runtime Environment (build 11.0.11+9-Ubuntu-0ubuntu2.20.04)
        OpenJDK 64-Bit Server VM (build 11.0.11+9-Ubuntu-0ubuntu2.20.04, mixed mode, sharing))`
    - `$ sudo apt install default-jdk`
    - `$ javac -version`
      - Output : \
      `javac 11.0.11`
### [Here is the reference to installing Java](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-20-04)

## How to Download the SDK
- Download the SDK from the GitHub repository
- `git clone git@github.com:arsrtech/accumulate-java-sdk.git`
- `cd accumulate-java-sdk`

## How to use it?
- Download the codebase and create a java file inside the src directory.
- Import and create a client.
- Then use all the client methods implemented in Java SDK.
### How to create the client
- The below client can connect with Accumulate public test net directly

``` 
TestNetClient client = new TestNetClient();
```
- If you need to connect your local DevNet then pass your local URL in your client

```
LocalDevNetClient localDevNetClient = new LocalDevNetClient("http://127.0.25.1:26660/v2");
```

#### [Check the complete documentation here](https://arsrtech.github.io/accumulate-java-sdk/doc/com/sdk/accumulate/service/Client.html)

## Quickstart tutorial
```
package com.sdk.accumulate.test;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.service.LiteAccount;
import com.sdk.accumulate.service.LocalDevNetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateADITest {

    private static final Logger logger = LoggerFactory.getLogger(CreateADITest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    public static void main(String[] args) throws Exception {
        LocalDevNetClient localDevNetClient = new LocalDevNetClient(baseUrl);
        LiteAccount liteAccount = LiteAccount.generate();
        String response = localDevNetClient.getFaucet(liteAccount.url().string());
        logger.info("Lite Account Response: {}",response);
        Thread.sleep(5000);


        AddCreditsArg addCreditsArg = new AddCreditsArg();
        addCreditsArg.setAmount(500000);
        addCreditsArg.setRecipient(liteAccount.url().string());
        String addCreditsResponse = localDevNetClient.addCredits(addCreditsArg,liteAccount);
        logger.info("Add Credits Response {} ",addCreditsResponse);
        Thread.sleep(5000);

        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl("acc://my-own-identity");
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = localDevNetClient.createIdentity(createIdentityArg,liteAccount);
        logger.info("Create ADI Response {} ",createAdiResponse);
    }
}
```