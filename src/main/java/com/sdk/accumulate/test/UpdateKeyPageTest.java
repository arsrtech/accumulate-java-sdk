package com.sdk.accumulate.test;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.enums.KeyPageOperation;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.UpdateKeyPageArg;
import com.sdk.accumulate.service.LiteAccount;
import com.sdk.accumulate.service.LocalDevNetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateKeyPageTest {

    private static final Logger logger = LoggerFactory.getLogger(UpdateKeyPageTest.class);

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
    }
}
