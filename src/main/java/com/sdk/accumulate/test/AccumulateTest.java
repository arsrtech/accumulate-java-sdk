package com.sdk.accumulate.test;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sdk.accumulate.controller.Accumulate;

public class AccumulateTest {
		
	private String baseUrl;
	private static int passed = 0;
	private static int failed = 0;
	
	public AccumulateTest(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	void testQuery() throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(this.baseUrl);
		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
		String response = accumulate.getQuery(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		JSONObject dataResult = (JSONObject)dataObj.get("data");
		if(accUrl.equals(dataResult.get("url"))) {
			passed++;
		}else {
			failed++;
		}
	}
	
	void testTransactionHistory() throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(this.baseUrl);
		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
		String response = accumulate.getTransactionHistory(accUrl,1);
		System.out.println(response);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		System.out.println(jobj);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		JSONArray dataResult = (JSONArray)dataObj.get("item");
		System.out.println(dataResult.get(0));
		if(accUrl.equals("fd")) {
			passed++;
		}else {
			failed++;
		}
	}
	
	void testTransaction() throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(this.baseUrl);
		String accUrl = "9dce91ec75f5b5e767283d8db77394daeef6e50b4f0e1197624f1a888ed076b1";
		String response = accumulate.getTransaction(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		if(accUrl.equals(dataObj.get("txid"))) {
			passed++;
		}else {
			failed++;
		}
	}
	
	void testQueryChain() throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(this.baseUrl);
		String chainId = "12e2d2d82f7b65752b3fd8d37d195f6d87f6cb24b83c4ae70f4571ea1007e741";
		String response = accumulate.getQueryChain(chainId);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		if(dataObj.get("data") != null) {
			passed++;
		}else {
			failed++;
		}
	}
	
	void testQueryData() throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(this.baseUrl);
		String accUrl = "acc://aditwo/aditwodata";
		String response = accumulate.getQueryData(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		if(dataObj.get("data") != null) {
			passed++;
		}else {
			failed++;
		}
	}
	
//	void testFaucet() throws ParseException, IOException {
//		Accumulate accumulate = new Accumulate(this.baseUrl);
//		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
//		String response = accumulate.getFaucet(accUrl);
//		JSONParser parser = new JSONParser();
//		JSONObject jobj = (JSONObject)parser.parse(response);
//		JSONObject dataObj = (JSONObject)jobj.get("result");
//		if(dataObj.get("hash") != null) {
//			passed++;
//		}else {
//			failed++;
//		}
//	}
	
	void testQueryKeyIndex() throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(this.baseUrl);
		String accUrl = "acc://adione/page0";
		String response = accumulate.getQueryKeyIndex(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		if(dataObj.get("hash") != null) {
			passed++;
		}else {
			failed++;
		}
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		AccumulateTest accTest = new AccumulateTest("https://testnet.accumulatenetwork.io/v2");
		accTest.testQuery();
		//accTest.testTransactionHistory();
		accTest.testTransaction();
		accTest.testQueryChain();
		accTest.testQueryData();
//		accTest.testFaucet();
		accTest.testQueryKeyIndex();
		System.out.println("Passed : "+passed+" Failed : "+failed);
	}
}
