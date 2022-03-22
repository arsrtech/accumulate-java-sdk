package com.sdk.accumulate.service;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccumulateTest {

	private static final String baseUrl = "http://127.0.25.1:26660/v2";


	@Test
	public void testQuery() throws ParseException, IOException {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
		String response = client.getQuery(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
	}

	@Test
	public void testTransactionHistory() throws ParseException, IOException {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
		String response = client.getTransactionHistory(accUrl,1);
		System.out.println(response);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		System.out.println(jobj);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		JSONArray dataResult = (JSONArray)dataObj.get("item");
	}

	@Test
	public void testTransaction() throws ParseException, IOException {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		String accUrl = "9dce91ec75f5b5e767283d8db77394daeef6e50b4f0e1197624f1a888ed076b1";
		String response = client.getTransaction(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
	}

	@Test
	public void testQueryChain() throws ParseException, IOException {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		String chainId = "12e2d2d82f7b65752b3fd8d37d195f6d87f6cb24b83c4ae70f4571ea1007e741";
		String response = client.getQueryChain(chainId);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
	}

	@Test
	public void testQueryData() throws ParseException, IOException {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		String accUrl = "acc://aditwo/aditwodata";
		String response = client.getQueryData(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
	}

	@Test
	public void testQueryKeyIndex() throws ParseException, IOException {
		LocalDevNetClient client = new LocalDevNetClient(baseUrl);
		String accUrl = "acc://adione/page0";
		String response = client.getQueryKeyIndex(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
	}
}
