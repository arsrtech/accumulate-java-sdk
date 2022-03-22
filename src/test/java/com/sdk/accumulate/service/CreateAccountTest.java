package com.sdk.accumulate.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountTest {

    private static final Logger logger = LoggerFactory.getLogger(CreateAccountTest.class);

    @Test
    public void testCreateAccount() throws Exception {
        TestNetClient testNetClient = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = testNetClient.getFaucet(liteAccount.url().string());
        logger.info("Lite Account Response: {}",response);
    }
 }
