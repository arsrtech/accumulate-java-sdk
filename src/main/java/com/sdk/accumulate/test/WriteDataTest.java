package com.sdk.accumulate.test;

import com.sdk.accumulate.model.WriteDataArg;
import com.sdk.accumulate.service.LiteAccount;
import com.sdk.accumulate.service.LocalDevNetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WriteDataTest {

	private static final Logger logger = LoggerFactory.getLogger(WriteDataTest.class);

	private static final String baseUrl = "http://127.0.25.1:26660/v2";

	public static void main(String[] args) throws Exception {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		LiteAccount liteAccount = LiteAccount.generate();
		String response = client.getFaucet(liteAccount.url().string());
		logger.info("Lite Account Response: {}",response);
		Thread.sleep(5000);

		WriteDataArg writeDataArg = new WriteDataArg();
		writeDataArg.setData("Test".getBytes(StandardCharsets.UTF_8));
		List<byte[]> bytes = new ArrayList<>();
		bytes.add(new byte[0]);
		writeDataArg.setExtIds(bytes);
		String writeDataResponse = client.writeData(writeDataArg,liteAccount);
		logger.info("Write data Response  Response {} ",writeDataResponse);
	}
}
