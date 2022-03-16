package com.sdk.accumulate.test;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.BurnTokensArg;
import com.sdk.accumulate.service.LiteAccount;
import com.sdk.accumulate.service.LocalDevNetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class BurnTokensTest {

    private static final Logger logger = LoggerFactory.getLogger(BurnTokensTest.class);

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

        BurnTokensArg burnTokensArg = new BurnTokensArg();
        burnTokensArg.setAmount(BigInteger.valueOf(1));
        String burnTokensResponse = localDevNetClient.burnTokens(burnTokensArg,liteAccount);
        logger.info("Burn token Response {} ",burnTokensResponse);
    }
}
