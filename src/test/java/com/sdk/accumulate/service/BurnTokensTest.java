package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.accumulate.model.AddCreditsArg;
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
        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        BurnTokensArg burnTokensArg = new BurnTokensArg();
        burnTokensArg.setAmount(BigInteger.valueOf(56879));
        String burnTokensResponse = client.burnTokens(burnTokensArg,liteAccount);
        System.out.println("Burn token Response: "+burnTokensResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(burnTokensResponse,RPCResponse.class);
        Assert.assertNotNull("Burn Tokens API Failed", rpcResponse.getResult().getTxid());
    }
}
