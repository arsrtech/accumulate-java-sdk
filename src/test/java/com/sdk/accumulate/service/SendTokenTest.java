package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.RPCResponse;
import com.sdk.accumulate.model.SendTokensArg;
import com.sdk.accumulate.model.TokenRecipientArg;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SendTokenTest {

    @Test
    public void testSendTokens() throws Exception {
        TestNetClient client = new TestNetClient();

        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        LiteAccount liteAccount2 = LiteAccount.generate();
        String response2 = client.getFaucet(liteAccount2.url().string());
        System.out.println("Lite Account Response: "+response2);

        SendTokensArg sendTokensArg = new SendTokensArg();
        List<TokenRecipientArg> to = new ArrayList<>();
        TokenRecipientArg tokenRecipientArg = new TokenRecipientArg();
        tokenRecipientArg.setAmount(20000000);
        tokenRecipientArg.setUrl(liteAccount2.url().string());
        to.add(tokenRecipientArg);
        sendTokensArg.setTo(to);
        String sendTokensResponse = client.sendToken(sendTokensArg,liteAccount);
        System.out.println("Send Token  Response: "+sendTokensResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(sendTokensResponse,RPCResponse.class);
        Assert.assertNotNull("Send Token Request failed", rpcResponse.getResult().getTxid());
    }
}
