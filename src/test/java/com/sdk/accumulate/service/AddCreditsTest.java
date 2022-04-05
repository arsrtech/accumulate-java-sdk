package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddCreditsTest {


    @Test
    public void testAddCredits() throws Exception{
        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        AddCreditsArg addCreditsArg = new AddCreditsArg();
        addCreditsArg.setAmount(1000);
        addCreditsArg.setRecipient(liteAccount.url().string());
        String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
        System.out.println("Add Credits Response: "+addCreditsResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(addCreditsResponse,RPCResponse.class);
        Assert.assertNotNull("Add Credits API failed", rpcResponse.getResult().getTxid());

    }
}
