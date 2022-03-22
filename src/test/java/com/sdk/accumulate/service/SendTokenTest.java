package com.sdk.accumulate.service;

import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.SendTokensArg;
import com.sdk.accumulate.model.TokenRecipientArg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SendTokenTest {

    private static final Logger logger = LoggerFactory.getLogger(SendTokenTest.class);

    private static final String baseUrl = "http://127.0.25.1:26660/v2";

    @Test
    public void testSendTokens() throws Exception {
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

        SendTokensArg sendTokensArg = new SendTokensArg();
//		sendTokensArg.setHash(new byte[0]);
//		sendTokensArg.setMeta(new byte[0]);
        List<TokenRecipientArg> to = new ArrayList<>();
        TokenRecipientArg tokenRecipientArg = new TokenRecipientArg();
        tokenRecipientArg.setAmount(20000000);
        tokenRecipientArg.setUrl(liteAccount2.url().string());
        to.add(tokenRecipientArg);
        sendTokensArg.setTo(to);
        String sendTokensResponse = client.sendToken(sendTokensArg,liteAccount);
        logger.info("Send Token  Response {} ",sendTokensResponse);
    }
}
