package com.sdk.accumulate.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.sdk.accumulate.model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Accumulate {

	private static final Logger logger = LoggerFactory.getLogger(Accumulate.class);
	
	private final String baseUrl;
	
	public Accumulate(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private String _execute(Payload payload, OriginSigner originSigner, String method, Object obj) throws Exception {
		HeaderOptions headerOptions = new HeaderOptions();
		headerOptions.setKeyPageHeight(1);
		headerOptions.setKeyPageIndex(originSigner.getKeyPageIndex());
		headerOptions.setNonce(new Date().getTime());
		logger.info("OrigSigner: {}",originSigner.getOrigin());
		logger.info("Header Options {}",headerOptions);
		Header header = new Header(originSigner.getOrigin().string(), headerOptions);
    	Transaction tx = new Transaction(payload, header);
		tx.sign(originSigner);
		logger.info("Signature {}",tx.getSignature().getSignature());
		TxnRequest txnRequest = tx.toTxRequest(true);
		txnRequest.setPayload(obj);
		return RPCClient.client(txnRequest,method);
	}
	
	@SuppressWarnings("unchecked")
	public String getQuery(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getTransactionHistory(String accUrl, int count) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_TX_HISTORY);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		param.put("count", count);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getTransaction(String txid) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_TRANSACTION);
		JSONObject param = new JSONObject();
		param.put("txid", txid);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getQueryChain(String chainId) throws ParseException, IOException {
		JSONObject obj = new JSONObject();		
		obj.put("method", Constant.QUERY_CHAIN);
		JSONObject param = new JSONObject();
		param.put("chainId", chainId);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getQueryData(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_DATA);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getFaucet(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.FAUCET);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	public String getQueryKeyIndex(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_KEY_INDEX);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	@SuppressWarnings("unchecked")
	public String addCredits(AddCreditsArg addCredits, OriginSigner originSigner) throws Exception {
		AddCreditsPayload addCreditsPayload = new AddCreditsPayload();
		addCreditsPayload.setAmount(addCredits.getAmount());
		addCreditsPayload.setRecipient(addCredits.getRecipient());
		addCreditsPayload.setType("addCredits");
		return this._execute(new AddCredits(addCredits),originSigner,"add-credits",addCreditsPayload);

	}



	@SuppressWarnings("unchecked")
	public String httpConnection(JSONObject obj, String endPointUrl) throws ParseException, IOException {
		obj.put("jsonrpc", "2.0");
		obj.put("id", 0);
		System.out.println(obj.toJSONString());
		URL url = new URL(endPointUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        
        OutputStream os = conn.getOutputStream();
        os.write(obj.toString().getBytes("UTF-8"));
        os.close();

        try {
        	//Get Response	
            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer(); 
            while((line = rd.readLine()) != null) {
              response.append(line);
              response.append('\r');
            }
            rd.close();
            return response.toString();
        }catch (Exception e) {
        	return "{\r\n" + 
        			"    \"authentication\": false,\r\n" + 
        			"}";
        }
		
	}

}
