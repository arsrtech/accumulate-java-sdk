# Accumulate Java SDK

## Summary
Accumulate SDK helps developers to develop the application with Accumulate network. It provides ready to use codebase with all Accumulate features. So they can create a new application with SDK or refactor the existing application.

## Release notes

| Release | Notes                             | Summary                                                    |
|---------|:----------------------------------|:-----------------------------------------------------------|
| 1.1     | Initial release with limited APIs | <ul><li>All Query APIs</li><li>Major create APIs</li></ul> |

## Prerequisite
- Java 11 or greater version

## How to use?
- Download the codebase and crate a java file inside src directory.
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

### How to use the client
- Client contains all Accumulate API features. You just need to call the method from client.
- The below code can generate a lite account and call accumulate network for GET Faucet
```
TestNetClient client = new TestNetClient();
LiteAccount liteAccount = LiteAccount.generate();
String response = client.getFaucet(liteAccount.url().string());
```
Note: In each client call you have to wait for few seconds to get the response from accumulate network

## Test cases
- To check the usage of all methods please check the test cases in the below directory
```
~/accumulate-java-sdk/src/test/java/com/sdk/accumulate/service/
```
- To run all the test cases at the same time, execute the below command 
```
mvn test
```
#### [Check the complete documentation here](https://arsrtech.github.io/accumulate-java-sdk/doc/com/sdk/accumulate/service/Client.html)
## Quick start tutorial
Demo of some main APIs of Accumulate:
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

````
