package com.sdk.accumulate.service;

import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.RPCResponse;
import com.sdk.accumulate.model.TxResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateADITest extends AbstractTestBase {

    @Test
    public void testCreateADI() throws Exception {
        //TestNetClient client = new TestNetClient();
        LocalDevNetClient client = new LocalDevNetClient("http://127.0.1.1:26660/v2");
        LiteAccount liteAccount = LiteAccount.generate();
        String response = client.getFaucet(liteAccount.url().string());
        System.out.println("Lite Account Response: "+response);
        RPCResponse rpcResponse = RPCResponse.from(response);
        TxResponse txResponse = rpcResponse.asTxPayload();
        Assert.assertNotNull(txResponse.getTxid());

        CreateIdentityArg createIdentityArg = new CreateIdentityArg();
        createIdentityArg.setUrl("acc://my-own-identity");
        TweetNaclFast.Signature.KeyPair kp = TweetNaclFast.Signature.keyPair();
        createIdentityArg.setPublicKey(kp.getPublicKey());
        createIdentityArg.setKeyBookName("test-key-book");
        createIdentityArg.setKeyPageName("test-key-page");
        String createAdiResponse = client.createIdentity(createIdentityArg,liteAccount);
        rpcResponse = RPCResponse.from(createAdiResponse);
        txResponse = rpcResponse.asTxPayload();
        Assert.assertNotNull(txResponse.getTxid());
    }
}
