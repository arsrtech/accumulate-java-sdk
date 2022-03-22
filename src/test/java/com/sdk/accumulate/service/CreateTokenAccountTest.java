package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateTokenAccountArg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class CreateTokenAccountTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateTokenAccountTest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @Test
    public void testCreateTokenAccount() throws Exception {

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
    }
}
