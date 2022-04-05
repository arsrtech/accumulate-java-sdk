package com.sdk.accumulate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwebpp.crypto.TweetNaclFast;
import com.sdk.accumulate.model.CreateIdentityArg;
import com.sdk.accumulate.model.RPCResponse;
import com.sdk.accumulate.model.WriteDataArg;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WriteDataTest {
	

	@Test
	public void testWriteData() throws Exception {
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

		WriteDataArg writeDataArg = new WriteDataArg();
		writeDataArg.setData("Test".getBytes(StandardCharsets.UTF_8));
		List<byte[]> bytes = new ArrayList<>();
		bytes.add(new byte[0]);
		writeDataArg.setExtIds(bytes);
		String writeDataResponse = client.writeData(writeDataArg,adi);
		System.out.println("Write data Response  Response: "+writeDataResponse);

		ObjectMapper objectMapper = new ObjectMapper();
		RPCResponse rpcResponse = objectMapper.readValue(writeDataResponse,RPCResponse.class);
		Assert.assertNotNull("Write Data Request failed", rpcResponse.getResult().getTxid());
	}
}
