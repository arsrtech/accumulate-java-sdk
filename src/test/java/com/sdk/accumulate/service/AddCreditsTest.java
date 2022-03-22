package com.sdk.accumulate.service;

import com.sdk.accumulate.model.AddCreditsArg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class AddCreditsTest {

    private static final Logger logger = LoggerFactory.getLogger(AddCreditsTest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @Test
    public void testAddCredits() throws Exception{
        LocalDevNetClient localDevNetClient = new LocalDevNetClient(baseUrl);
        LiteAccount liteAccount = LiteAccount.generate();
        String response = localDevNetClient.getFaucet(liteAccount.url().string());
        logger.info("Lite Account Response: {}",response);
        Thread.sleep(5000);


        AddCreditsArg addCreditsArg = new AddCreditsArg();
        addCreditsArg.setAmount(1000);
        addCreditsArg.setRecipient(liteAccount.url().string());
        String addCreditsResponse = localDevNetClient.addCredits(addCreditsArg,liteAccount);
        logger.info("Add Credits Response {} ",addCreditsResponse);
    }
}
