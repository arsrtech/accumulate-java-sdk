package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.AddCreditsArg;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateKeyBookArg;
import com.sdk.accumulate.model.RPCResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CreateKeyBookTest {

    @Test
    public void testCreateKeyBook() throws Exception{

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

        CreateKeyBookArg createKeyBookArg = new CreateKeyBookArg();
        createKeyBookArg.setUrl(identityUrl+"/book1");
        List<String> pages = new ArrayList<>();
        pages.add(identityUrl+"/test-key-page");
        createKeyBookArg.setPages(pages);
        String createKeyBook = client.createKeyBook(createKeyBookArg,adi);
        System.out.println("Create Key book Response: "+createKeyBook);

        ObjectMapper objectMapper = new ObjectMapper();
        RPCResponse rpcResponse = objectMapper.readValue(createKeyBook,RPCResponse.class);
        Assert.assertNotNull("Create Key Book Request failed", rpcResponse.getResult().getTxid());
    }
}
