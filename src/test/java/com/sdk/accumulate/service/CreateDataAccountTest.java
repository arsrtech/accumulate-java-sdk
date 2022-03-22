package com.sdk.accumulate.service;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateDataAccountArg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class CreateDataAccountTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateDataAccountTest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @Test
    public void testCreateDataAccount() throws Exception {
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

        CreateDataAccountArg createDataAccountArg = new CreateDataAccountArg();
        createDataAccountArg.setUrl(liteAccount.url().string()+"/data");
        createDataAccountArg.setKeyBookUrl("acc://pun1/pun1book");
        createDataAccountArg.setManagerKeyBookUrl("acc://pun1/pun1book");
        createDataAccountArg.setScratch(true);
        String createDataAccountResponse = localDevNetClient.createDataAccount(createDataAccountArg,liteAccount);
        logger.info("Create data account Response {} ",createDataAccountResponse);
    }
}
