package com.sdk.accumulate.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sdk.accumulate.enums.RequestMethods;
import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.*;
import com.sdk.accumulate.payload.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	
	private final String baseUrl;
	
	public Client(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private String _execute(Payload payload, OriginSigner originSigner, String method, Object obj) throws Exception {
		HeaderOptions headerOptions = new HeaderOptions();
		headerOptions.setKeyPageHeight(1);
//		headerOptions.setKeyPageIndex(0);
		headerOptions.setNonce(2949658590L);
		logger.info("OrigSigner: {}",originSigner.getOrigin());
		logger.info("Header Options {}",headerOptions);
		Header header = new Header(originSigner.getOrigin().string(), headerOptions);
    	Transaction tx = new Transaction(payload, header);
		tx.sign(originSigner);
		logger.info("Signature {}",tx.getSignature());
		TxnRequest txnRequest = tx.toTxRequest(true);
//		txnRequest.setPayload(obj);
		return RPCClient.client(txnRequest,"execute");
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
		addCreditsPayload.setType(TxnType.AddCredits.getValue());
		return this._execute(new AddCredits(addCredits),originSigner, RequestMethods.AddCredits.getValue(), addCreditsPayload);

	}

	@SuppressWarnings("unchecked")
	public String burnTokens(BurnTokensArg burnTokensArg, OriginSigner originSigner) throws Exception {
		BurnTokensPayload burnTokensPayload = new BurnTokensPayload();
		burnTokensPayload.setAmount(String.valueOf(burnTokensArg.getAmount()));
		burnTokensPayload.setType(TxnType.BurnTokens.getValue());
		return this._execute(new BurnTokens(burnTokensArg),originSigner,RequestMethods.BurnTokens.getValue(), burnTokensPayload);

	}

	@SuppressWarnings("unchecked")
	public String createDataAccount(CreateDataAccountArg createDataAccountArg, OriginSigner originSigner) throws Exception {
		CreateDataAccountPayload createDataAccountPayload = new CreateDataAccountPayload();
		createDataAccountPayload.setType(TxnType.CreateDataAccount.getValue());
		createDataAccountPayload.setUrl(createDataAccountArg.getUrl());
		createDataAccountPayload.setKeyBookUrl(createDataAccountArg.getKeyBookUrl());
		createDataAccountPayload.setManagerKeyBookUrl(createDataAccountArg.getManagerKeyBookUrl());
		createDataAccountPayload.setScratch(createDataAccountArg.isScratch());
		return this._execute(new CreateDataAccount(createDataAccountArg),originSigner,RequestMethods.CreateDataAccount.getValue(), createDataAccountPayload);
	}

	@SuppressWarnings("unchecked")
	public String createIdentity(CreateIdentityArg createIdentityArg, OriginSigner originSigner) throws Exception {
		CreateIdentityPayload createIdentityPayload = new CreateIdentityPayload();
		createIdentityPayload.setType(TxnType.CreateIdentity.getValue());
		createIdentityPayload.setUrl(createIdentityArg.getUrl());
		createIdentityPayload.setKeyBookName(createIdentityArg.getKeyBookName());
		createIdentityPayload.setPublicKey(createIdentityArg.getPublicKey());
		createIdentityPayload.setKeyPageName(createIdentityArg.getKeyPageName());
		return this._execute(new CreateIdentity(createIdentityArg),originSigner,RequestMethods.CreateIdentity.getValue(), createIdentityPayload);
	}

	@SuppressWarnings("unchecked")
	public String createKeyBook(CreateKeyBookArg createKeyBookArg, OriginSigner originSigner) throws Exception {
		CreateKeyBookPayload createKeyBookPayload = new CreateKeyBookPayload();
		createKeyBookPayload.setType(TxnType.CreateKeyBook.getValue());
		createKeyBookPayload.setUrl(createKeyBookArg.getUrl());
		createKeyBookPayload.setPages(createKeyBookArg.getPages());
		return this._execute(new CreateKeyBook(createKeyBookArg),originSigner,RequestMethods.CreateKeyBook.getValue(), createKeyBookPayload);
	}

	@SuppressWarnings("unchecked")
	public String createKeyPage(CreateKeyPageArg createKeyPageArg, OriginSigner originSigner) throws Exception {
		CreateKeyPagePayload createKeyPagePayload = new CreateKeyPagePayload();
		createKeyPagePayload.setType(TxnType.CreateKeyPage.getValue());
		createKeyPagePayload.setUrl(createKeyPageArg.getUrl());
		List<String> keys = new ArrayList<>();
		for (byte[] key: createKeyPageArg.getKeys()) {
			keys.add(Crypto.toHexString(key));
		}
		createKeyPagePayload.setKeys(keys);
		return this._execute(new CreateKeyPage(createKeyPageArg),originSigner,RequestMethods.CreateKeyPage.getValue(), createKeyPagePayload);
	}

	@SuppressWarnings("unchecked")
	public String createTokenAccount(CreateTokenAccountArg createTokenAccountArg, OriginSigner originSigner) throws Exception {
		CreateTokenAccountPayload createTokenAccountPayload = new CreateTokenAccountPayload();
		createTokenAccountPayload.setType(TxnType.CreateTokenAccount.getValue());
		createTokenAccountPayload.setUrl(createTokenAccountArg.getTokenUrl());
		createTokenAccountPayload.setTokenUrl(createTokenAccountArg.getTokenUrl());
		createTokenAccountPayload.setKeyBookUrl(createTokenAccountArg.getKeyBookUrl());
		createTokenAccountPayload.setScratch(createTokenAccountArg.isScratch());
		return this._execute(new CreateTokenAccount(createTokenAccountArg),originSigner,RequestMethods.CreateTokenAccount.getValue(), createTokenAccountPayload);
	}


	public String createToken(CreateTokenArg createTokenArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateToken(createTokenArg),originSigner,RequestMethods.CreateToken.getValue(), null);
	}


	public String issueTokens(IssueTokensArg issueTokensArg, OriginSigner originSigner) throws Exception {
		return this._execute(new IssueTokens(issueTokensArg),originSigner,RequestMethods.IssueToken.getValue(), null);
	}


	public String sendToken(SendTokensArg sendTokensArg, OriginSigner originSigner) throws Exception {
		return this._execute(new SendTokens(sendTokensArg),originSigner,"execute", null);

	}


	public String updateKeyPage(UpdateKeyPageArg updateKeyPageArg, OriginSigner originSigner) throws Exception {
		return this._execute(new UpdateKeyPage(updateKeyPageArg),originSigner,RequestMethods.UpdateKeyPage.getValue(), null);
	}


	public String writeData(WriteDataArg writeDataArg, OriginSigner originSigner) throws Exception {
		return this._execute(new WriteData(writeDataArg),originSigner,RequestMethods.WriteData.getValue(), null);
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