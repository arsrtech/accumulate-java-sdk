package com.sdk.accumulate.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccumulateTest {

	@Test
	public void testQuery() throws Exception {
		TestNetClient client = new TestNetClient();
		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
		String response = client.getQuery(accUrl);
		System.out.println("Response: "+response);
	}

	@Test
	public void testTransactionHistory() throws Exception {
		TestNetClient client = new TestNetClient();
		String accUrl = "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME";
		String response = client.getTransactionHistory(accUrl,1);
		System.out.println("Response: "+response);
	}

	@Test
	public void testTransaction() throws Exception {
		TestNetClient client = new TestNetClient();
		String accUrl = "9dce91ec75f5b5e767283d8db77394daeef6e50b4f0e1197624f1a888ed076b1";
		String response = client.getTransaction(accUrl);
		System.out.println("Response: "+response);
	}

	@Test
	public void testQueryChain() throws Exception {
		TestNetClient client = new TestNetClient();
		String chainId = "12e2d2d82f7b65752b3fd8d37d195f6d87f6cb24b83c4ae70f4571ea1007e741";
		String response = client.getQueryChain(chainId);
		System.out.println("Response: "+response);
	}

	@Test
	public void testQueryData() throws Exception {
		TestNetClient client = new TestNetClient();
		String accUrl = "acc://aditwo/aditwodata";
		String response = client.getQueryData(accUrl);
		System.out.println("Response: "+response);
	}

	@Test
	public void testQueryKeyIndex() throws Exception {
		TestNetClient client = new TestNetClient();
		String accUrl = "acc://adione/page0";
		String response = client.getQueryKeyIndex(accUrl);
		System.out.println("Response: "+response);
	}
}
