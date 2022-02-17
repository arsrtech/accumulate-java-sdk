package com.sdk.accumulate.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.sdk.accumulate.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Accumulate {

	private static final Logger logger = LoggerFactory.getLogger(Accumulate.class);
	
	private String baseUrl;
	
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
	public String getCreateAdi(String accUrl) throws ParseException, IOException {
		Accumulate accumulate = new Accumulate(accUrl);
		String response = accumulate.getTransaction(accUrl);
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(response);
		JSONObject dataObj = (JSONObject)jobj.get("result");
		
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.CREATE_ADI);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "433b80aa4be172d05c55f86bf6aca5313c7a6a2c76784ac92d109420a18e6a5bb11a411e78b2ea437c4d83eca139d918fb414f01ab36782e81eb2b69ba5d6407");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "0f1d20afd36f778df9692efa5b69bcf04adfe8cf7a4c12e50e4523bebab52825");
		signer.put("nonce", 1642405755442626L);
		param.put("signer", dataObj.get("signer"));
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", dataObj.get("keyPage"));
		
		JSONObject payload = new JSONObject();
		payload.put("url", "pun8");
		payload.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		payload.put("keyBookName", "pun5book");
		payload.put("keyPageName", "pun5page");
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String getCreateDataAccount(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.CREATE_DATA_ACCOUNT);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "be51cb7dcacd38943bb7d18e72b360e79ac1533ae933de76e7071db4f91d660ecad134808688e0c69d34ead11fbe2f6751dbbceedf8f74448635bd7aa5e0b60b");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		signer.put("nonce", 1642415962518690L);
		param.put("signer", signer);
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", keyPage);
		
		JSONObject payload = new JSONObject();
		payload.put("url", "acc://pun1/data");
		payload.put("keyBookUrl", "acc://pun1/pun1book");
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String getCreateTokenAccount(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.CREATE_TOKEN_ACCOUNT);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "8f70fd61875ee45e4743d0199a5705f19901cb6f34f65fe3fab7e5c07f86008dba656d04dfe2fda8d9e659610716938b8131560c3aaf34c2c45cad2204873605");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		signer.put("nonce", 1642416249257761L);
		param.put("signer", signer);
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", keyPage);
		
		JSONObject payload = new JSONObject();
		payload.put("url", "acc://pun1/tok");
		payload.put("tokenUrl", "acc://ACME");
		payload.put("keyBookUrl", "acc://pun1/pun1book");
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String getCreateKeyPage(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.CREATE_KEY_PAGE);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "215ecd3c35da005687fa8eb54139067c9f5bbfe5e200a5a5b54e0f43492cbbd0dba47ab8814a21124f264fd85a6e9d54688275550d3adc45a5bdd3c4c82f1508");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		signer.put("nonce", 1642419400242766L);
		param.put("signer", signer);
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", keyPage);
		
		JSONObject payload = new JSONObject();
		payload.put("url", "acc://pun123/pg123");
		
		JSONObject publicKey = new JSONObject();
		publicKey.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		
		JSONArray keyArr = new JSONArray();
		keyArr.add(publicKey);
		payload.put("keys", keyArr);
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String getCreateKeyBook(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.CREATE_KEY_BOOK);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "215ecd3c35da005687fa8eb54139067c9f5bbfe5e200a5a5b54e0f43492cbbd0dba47ab8814a21124f264fd85a6e9d54688275550d3adc45a5bdd3c4c82f1508");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		signer.put("nonce", 1642419400242766L);
		param.put("signer", signer);
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", keyPage);
		
		JSONObject payload = new JSONObject();
		payload.put("url", "acc://pun123/pg123");
		
		JSONArray keyArr = new JSONArray();
		keyArr.add("76037579e7e45b863ef34705c9931a5191faa01e85088a8e636d5f70472e0730");
		payload.put("pages", keyArr);
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String getWriteData(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.WRITE_DATA);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "215ecd3c35da005687fa8eb54139067c9f5bbfe5e200a5a5b54e0f43492cbbd0dba47ab8814a21124f264fd85a6e9d54688275550d3adc45a5bdd3c4c82f1508");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		signer.put("nonce", 1642419400242766L);
		param.put("signer", signer);
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", keyPage);
		
		JSONObject payload = new JSONObject();
		JSONObject entry = new JSONObject();
		entry.put("data", "acc://pun123/pg123");
		payload.put("entry", entry);
		
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String getSendToken(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.SEND_TOKEN);
		JSONObject param = new JSONObject();
		param.put("origin", accUrl);
		param.put("sponsor", accUrl);
		param.put("signature", "215ecd3c35da005687fa8eb54139067c9f5bbfe5e200a5a5b54e0f43492cbbd0dba47ab8814a21124f264fd85a6e9d54688275550d3adc45a5bdd3c4c82f1508");
		
		JSONObject signer = new JSONObject();
		signer.put("publicKey", "31c52df25166098c9f227e5bd4d39e1ef5c0ab9a6c4fcc327f27e3e38eab44b6");
		signer.put("nonce", 1642419400242766L);
		param.put("signer", signer);
		
		JSONObject keyPage = new JSONObject();
		keyPage.put("height", 1);
		param.put("keyPage", keyPage);
		
		JSONObject payload = new JSONObject();
		payload.put("hash", "0000000000000000000000000000000000000000000000000000000000000000");
		
		JSONObject to = new JSONObject();
		to.put("url", "0000000000000000000000000000000000000000000000000000000000000000");
		to.put("amount", "0000000000000000000000000000000000000000000000000000000000000000");
		JSONArray toArr = new JSONArray();
		toArr.add(to);
		payload.put("to", toArr);
		
		param.put("payload", payload);
		
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
	}
	
	@SuppressWarnings("unchecked")
	public String addCredits(AddCreditsArg addCredits, OriginSigner originSigner) throws Exception {
		AddCreditsPayload addCreditsPayload = new AddCreditsPayload();
		addCreditsPayload.setAmount(BigInteger.valueOf(addCredits.getAmount()));
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
