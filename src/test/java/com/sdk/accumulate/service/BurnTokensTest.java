package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.accumulate.model.BurnTokensArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

@RunWith(MockitoJUnitRunner.class)
public class BurnTokensTest {
    
    @Test
    public void testBurnTokens() throws Exception {
        LocalDevNetClient client = new LocalDevNetClient("http://127.0.25.1:26660/v2");
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        response = client.getQuery(liteAccount.url.string());
        System.out.println(response);

        Thread.sleep(10000);
        BurnTokensArg burnTokensArg = new BurnTokensArg();
        burnTokensArg.setAmount(BigInteger.valueOf(1));
        String burnTokensResponse = client.burnTokens(burnTokensArg,liteAccount);
        System.out.println("Burn token Response: "+burnTokensResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(burnTokensResponse,RPCResponse.class);
        Assert.assertNotNull("Burn Tokens API Failed", rpcResponse.getResponseResult().getTxid());
    }
}
