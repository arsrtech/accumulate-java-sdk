package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.IssueTokensArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IssueTokenTest {

    @Test
    public void testIssueTokens() throws Exception {
        TestNetClient client = new TestNetClient();

        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        LiteAccount liteAccount2 = LiteAccount.generate();
        String response2 = client.getFaucet(liteAccount2.url().string());
        System.out.println("Lite Account Response: "+response2);

        IssueTokensArg issueTokensArg = new IssueTokensArg();
        issueTokensArg.setRecipient(liteAccount2.url().string());
        issueTokensArg.setAmount(1);
        String issueTokensResponse = client.issueTokens(issueTokensArg,liteAccount);
        System.out.println("Issue Token  Response: "+issueTokensResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(issueTokensResponse,RPCResponse.class);
        Assert.assertNotNull("Issue Token Request failed", rpcResponse.getResult().getTxid());
    }
}
