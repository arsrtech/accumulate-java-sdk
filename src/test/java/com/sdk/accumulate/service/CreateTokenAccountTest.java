package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.CreateTokenAccountArg;
import com.sdk.accumulate.model.RPCResponse;
import com.sdk.accumulate.model.TxResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateTokenAccountTest extends AbstractTestBase {

    @Test
    public void testCreateTokenAccount() throws Exception {

        TestNetClient client = new TestNetClient();
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);

        String identityUrl = "acc://my-own-identity-2";
        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl(identityUrl);
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
        RPCResponse rpcResponse = RPCResponse.from(createAdiResponse);
        TxResponse txResponse = rpcResponse.asTxPayload();
        Assert.assertNotNull(txResponse.getTxid());
        ADI adi = new ADI(AccURL.toAccURL(identityUrl),kp);

        CreateTokenAccountArg createTokenAccountArg = new CreateTokenAccountArg();
        createTokenAccountArg.setUrl(identityUrl);
        createTokenAccountArg.setTokenUrl(identityUrl+"/tok");
        createTokenAccountArg.setKeyBookUrl(identityUrl+"/test-key-book");
        createTokenAccountArg.setScratch(true);
        String createTokenAccountResponse = client.createTokenAccount(createTokenAccountArg,adi);
        System.out.println("Create Token Account Response: "+createTokenAccountResponse);
        rpcResponse = RPCResponse.from(createAdiResponse);
        txResponse = rpcResponse.asTxPayload();
        Assert.assertNotNull(txResponse.getTxid());
    }
}
