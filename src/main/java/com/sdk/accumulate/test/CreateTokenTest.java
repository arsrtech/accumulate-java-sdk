package com.sdk.accumulate.test;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateTokenArg;
import com.sdk.accumulate.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class CreateTokenTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateTokenTest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    public static void main(String[] args) throws Exception {

        LocalDevNetClient client = new LocalDevNetClient(baseUrl);
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
    }
}
