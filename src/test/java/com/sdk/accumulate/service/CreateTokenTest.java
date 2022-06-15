package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateTokenArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

@RunWith(MockitoJUnitRunner.class)
public class CreateTokenTest {

    @Test
    public void testCreateToken() throws Exception {

        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        AddCreditsArg addCreditsArg = new AddCreditsArg();
        addCreditsArg.setAmount(500000);
        addCreditsArg.setRecipient(liteAccount.url().string());
        String addCreditsResponse = client.addCredits(addCreditsArg,liteAccount);
        System.out.println("Add Credits Response: "+addCreditsResponse);

        String identityUrl = "acc://my-own-identity-3";
        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl(identityUrl);
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
        System.out.println("Create ADI Response: "+createAdiResponse);
        Thread.sleep(5000);
        ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

        CreateTokenArg createTokenArg = new CreateTokenArg();
        createTokenArg.setUrl(identityUrl+"/tok");
        createTokenArg.setKeyBookUrl(identityUrl+"/test-key-page");
        createTokenArg.setSymbol("INR");
        createTokenArg.setPrecision(10);
        createTokenArg.setProperties("acc://my-own-identity-3/INR");
        createTokenArg.setInitialSupply(BigInteger.valueOf(1000000000));
        createTokenArg.setHasSupplyLimit(true);
        createTokenArg.setManager("acc://my-own-identity-3/test-key-page");
        String createTokenResponse = client.createToken(createTokenArg,adi);
        System.out.println("Create Token Response: "+createTokenResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(createTokenResponse,RPCResponse.class);
        Assert.assertNotNull("Create Token Request failed", rpcResponse.getResponseResult().getTxid());
    }
}
