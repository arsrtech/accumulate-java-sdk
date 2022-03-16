package com.sdk.accumulate.test;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.IssueTokensArg;
import com.sdk.accumulate.service.LiteAccount;
import com.sdk.accumulate.service.LocalDevNetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTokenTest {

    private static final Logger logger = LoggerFactory.getLogger(IssueTokenTest.class);

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

        LiteAccount liteAccount2 = LiteAccount.generate();
        String response2 = client.getFaucet(liteAccount2.url().string());
        logger.info("Lite Account Response: {}",response2);
        Thread.sleep(5000);

        IssueTokensArg issueTokensArg = new IssueTokensArg();
        issueTokensArg.setRecipient(liteAccount2.url().string());
        issueTokensArg.setAmount(1);
        String issueTokensResponse = client.issueTokens(issueTokensArg,liteAccount);
        logger.info("Issue Token  Response {} ",issueTokensResponse);
    }
}
