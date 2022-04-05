package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateADITest {

    @Test
    public void testCreateADI() throws Exception {
        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl("acc://my-own-identity");
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
        System.out.println("Create ADI Response: "+createAdiResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(createAdiResponse,RPCResponse.class);
        Assert.assertNotNull("Create ADI request failed", rpcResponse.getResult().getTxid());
    }
}
