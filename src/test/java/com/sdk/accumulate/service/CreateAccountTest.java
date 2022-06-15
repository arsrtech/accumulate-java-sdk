package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateAccountTest {
    
    @Test
    public void testCreateAccount() throws Exception {
        TestNetClient testNetClient = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = testNetClient.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(response,RPCResponse.class);
        Assert.assertNotNull("Account Creation failed", rpcResponse.getResponseResult().getTxid());
    }
 }
