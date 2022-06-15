package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.enums.KeyPageOperation;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.RPCResponse;
import com.sdk.accumulate.model.UpdateKeyPageArg;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpdateKeyPageTest {

    @Test
    public void testUpdateKeyPage() throws Exception {
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

        UpdateKeyPageArg updateKeyPageArg = new UpdateKeyPageArg();
        updateKeyPageArg.setOwner("acc://my-own-identity");
        TweetNaclFast.Signature.KeyPair kp1 = TweetNaclFast.Signature.keyPair();
        updateKeyPageArg.setNewKey(kp1.getPublicKey());
        updateKeyPageArg.setKey(kp.getPublicKey());
        updateKeyPageArg.setOperation(KeyPageOperation.UpdateKey);
//		updateKeyPageArg.setThreshold(1);
        String updateKeyPageResponse = client.updateKeyPage(updateKeyPageArg,liteAccount);
        System.out.println("update KeyPage Response  Response: "+updateKeyPageResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(updateKeyPageResponse,RPCResponse.class);
        Assert.assertNotNull("Update Key Request failed", rpcResponse.getResponseResult().getTxid());
    }
}
