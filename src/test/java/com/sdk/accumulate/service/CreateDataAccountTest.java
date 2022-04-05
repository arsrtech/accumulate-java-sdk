package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.CreateDataAccountArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateDataAccountTest {
    
    @Test
    public void testCreateDataAccount() throws Exception {
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

        CreateDataAccountArg createDataAccountArg = new CreateDataAccountArg();
        createDataAccountArg.setUrl(identityUrl+"/data");
        createDataAccountArg.setKeyBookUrl(identityUrl+"/test-key-book");
        createDataAccountArg.setManagerKeyBookUrl(identityUrl+"/test-key-book");
        createDataAccountArg.setScratch(true);
        String createDataAccountResponse = client.createDataAccount(createDataAccountArg,adi);
        System.out.println("Create data account Response: "+createDataAccountResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(createDataAccountResponse,RPCResponse.class);
        Assert.assertNotNull("Create Data Account Request failed", rpcResponse.getResult().getTxid());
    }
}
