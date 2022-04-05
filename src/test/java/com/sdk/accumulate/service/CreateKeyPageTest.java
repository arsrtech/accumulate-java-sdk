package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateKeyPageArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CreateKeyPageTest {

    @Test
    public void testCreateKeyPage() throws Exception{
        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        String identityUrl = "acc://my-own-identity-1";
        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl(identityUrl);
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
        System.out.println("Create ADI Response: "+createAdiResponse);
        ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

        CreateKeyPageArg createKeyPageArg = new CreateKeyPageArg();
        List<byte[]> keys = new ArrayList<>();
        keys.add(liteAccount.getPublicKey());
        createKeyPageArg.setKeys(keys);
        createKeyPageArg.setUrl(identityUrl+"/test-key-page");
        String createKeyPageResponse = client.createKeyPage(createKeyPageArg,adi);
        System.out.println("Create Key page Response: "+createKeyPageResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(createKeyPageResponse,RPCResponse.class);
        Assert.assertNotNull("Create Key Page Request failed", rpcResponse.getResult().getTxid());
    }
}
