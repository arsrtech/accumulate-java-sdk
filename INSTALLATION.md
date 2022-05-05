# Accumulate Java SDK installation Guide

## Introduction:
Accumulate Java SDK helps the developers to integrate their applications with Accumulate network. It has ready-made methods to execute. So the developers can reuse the codebase and develop their application efficiently.

## Requirements
- Java 11 or greater version
- maven 3.6 or greater version

## Java installation
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

## SDK Installation
- Add the below dependency in the new/existing project's pom.xml file 
```
    <repositories>
        <repository>
            <id>com.sdk.accumulate</id>
            <url>http://18.232.151.167:8082/artifactory/libs-release-local/</url>
        </repository>
    </repositories>
    
      <dependencies>
    <dependency>
        <groupId>com.sdk.accumulate</groupId>
        <artifactId>AccumulateJavaSDK</artifactId>
        <version>1.1</version>
    </dependency>
    </dependencies>
```

- Now the SDK is available in the new/existing project.
- Check the below screenshot for reference

![](https://github.com/arsrtech/accumulate-java-sdk/blob/master/src/main/resources/sdk_install.png)
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

## Configure the  Java SDK repo (settings.xml configuration)
- Currently, the Java SDK hosted in a private repository. To access the repository we have to specify the username 
and password of the repository.
- To set the credentials, we have to create a settings file inside the .m2 directory.
- Since we are using the maven, the system itself can create the .m2 directory while maven installation.
- Else follow the below steps.
  - `$ cd`  - It will goto the home directory. (for example `/home/user/`)
  - `$ mkdir .m2` - It will create a .m2 directory (`/home/user/.m2/`)
  - Now create a **settings.xml** file inside the .m2 directory (`/home/user/.m2/settings.xml`)
  - Then paste the below xml content in the **settings.xml** file.
```
<?xml version="1.0" encoding="UTF-8"?>
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 http://maven.apache.org/xsd/settings-1.2.0.xsd" xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <servers>
        <server>
            <username>admin</username>
            <password>Admin@123</password>
            <id>central</id>
        </server>
    </servers>
    <mirrors>
        <mirror>
            <id>insecure-repo</id>
            <mirrorOf>external:http:*</mirrorOf>
            <url>http://18.232.151.167:8082/artifactory/libs-release-local/</url>
            <blocked>false</blocked>
        </mirror>
    </mirrors>
</settings>
```
- And save the file
- Now the system can access the Java SDK private repository.
