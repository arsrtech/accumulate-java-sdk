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

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	
	public final String baseUrl;
	
	public Client(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private String _execute(Payload payload, OriginSigner originSigner) throws Exception {
		HeaderOptions headerOptions = new HeaderOptions();
		headerOptions.setKeyPageHeight(1);
//		headerOptions.setKeyPageIndex(0);
		headerOptions.setNonce(new Date().getTime());
		Header header = new Header(originSigner.getOrigin().string(), headerOptions);
    	Transaction tx = new Transaction(payload, header);
		tx.sign(originSigner);
		TxnRequest txnRequest = tx.toTxRequest(true);
		return RPCClient.client(this.baseUrl,txnRequest,"execute");
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

	public String addCredits(AddCreditsArg addCredits, OriginSigner originSigner) throws Exception {
		return this._execute(new AddCredits(addCredits),originSigner);

	}

	public String burnTokens(BurnTokensArg burnTokensArg, OriginSigner originSigner) throws Exception {
		return this._execute(new BurnTokens(burnTokensArg),originSigner);

	}

	public String createDataAccount(CreateDataAccountArg createDataAccountArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateDataAccount(createDataAccountArg),originSigner);
	}

	public String createIdentity(CreateIdentityArg createIdentityArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateIdentity(createIdentityArg),originSigner);
	}

	public String createKeyBook(CreateKeyBookArg createKeyBookArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateKeyBook(createKeyBookArg),originSigner);
	}

	public String createKeyPage(CreateKeyPageArg createKeyPageArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateKeyPage(createKeyPageArg),originSigner);
	}

	public String createTokenAccount(CreateTokenAccountArg createTokenAccountArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateTokenAccount(createTokenAccountArg),originSigner);
	}


	public String createToken(CreateTokenArg createTokenArg, OriginSigner originSigner) throws Exception {
		return this._execute(new CreateToken(createTokenArg),originSigner);
	}


	public String issueTokens(IssueTokensArg issueTokensArg, OriginSigner originSigner) throws Exception {
		return this._execute(new IssueTokens(issueTokensArg),originSigner);
	}


	public String sendToken(SendTokensArg sendTokensArg, OriginSigner originSigner) throws Exception {
		return this._execute(new SendTokens(sendTokensArg),originSigner);

	}


	public String updateKeyPage(UpdateKeyPageArg updateKeyPageArg, OriginSigner originSigner) throws Exception {
		return this._execute(new UpdateKeyPage(updateKeyPageArg),originSigner);
	}


	public String writeData(WriteDataArg writeDataArg, OriginSigner originSigner) throws Exception {
		return this._execute(new WriteData(writeDataArg),originSigner);
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
