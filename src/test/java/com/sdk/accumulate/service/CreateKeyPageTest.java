package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateKeyPageArg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CreateKeyPageTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateKeyPageTest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @Test
    public void testCreateKeyPage() throws Exception{
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
    }
}
