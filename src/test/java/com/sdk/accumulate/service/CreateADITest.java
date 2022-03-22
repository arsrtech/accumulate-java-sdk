package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class CreateADITest {

    private static final Logger logger = LoggerFactory.getLogger(CreateADITest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @Test
    public void testCreateADI() throws Exception {
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
