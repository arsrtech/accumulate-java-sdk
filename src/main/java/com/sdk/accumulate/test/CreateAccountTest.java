package com.sdk.accumulate.test;

import com.sdk.accumulate.service.Client;
import com.sdk.accumulate.service.LiteAccount;
import com.sdk.accumulate.service.TestNetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAccountTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateAccountTest.class);

    public static void main(String[] args) throws Exception {
        TestNetClient testNetClient = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = testNetClient.getFaucet(liteAccount.url().string());
        logger.info("Lite Account Response: {}",response);
    }
 }
