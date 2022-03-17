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

	/**
	 * Accumulate network Url (That may be a test net or Main net or local Devnet)
	 */
	public final String baseUrl;

	/**
	 * @param baseUrl If you are connecting other than test net you have to provide the Accumulate network URL
	 */
	public Client(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * It will directly connect with Accumulate public test net  (https://testnet.accumulatenetwork.io/v2)
	 */
	public Client() {
		this.baseUrl = "https://testnet.accumulatenetwork.io/v2";
	}

	/**
	 * Method used to execute all RPC calls
	 * @param payload This object contains all the payload parameters. The Parameters can vary for each RPC method
	 * @param originSigner The Signer of the RPC call (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Returns RPC Call Response as Json String
	 * @throws Exception Throws Exception In case of RPC call failure or Parser failure
	 */
	private String execute(Payload payload, OriginSigner originSigner) throws Exception {
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

	/**
	 * Method used to query about a lite account. It will return the details of a specific account
	 * @param accUrl  Account URL
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getQuery(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	/**
	 * Method used to get the transaction history based on account
	 * @param accUrl Account URL
	 * @param count Transaction history count
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
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

	/**
	 * Method used to get the transaction details based on transaction ID
	 * @param txid Transaction Reference ID
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getTransaction(String txid) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_TRANSACTION);
		JSONObject param = new JSONObject();
		param.put("txid", txid);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	/**
	 * Method used to get the transaction based on chain ID
	 * @param chainId Transaction chain ID
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getQueryChain(String chainId) throws ParseException, IOException {
		JSONObject obj = new JSONObject();		
		obj.put("method", Constant.QUERY_CHAIN);
		JSONObject param = new JSONObject();
		param.put("chainId", chainId);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	/**
	 * Method used to query the Data based on Account
	 * @param accUrl Account URL
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getQueryData(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_DATA);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	/**
	 * Method used to Get faucet for an account. It also activates an account with the network
	 * @param accUrl Account URL
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getFaucet(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.FAUCET);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	/**
	 * Method used query the Key index based on Account
	 * @param accUrl Account URL
	 * @return Returns RPC Call Response as Json String
	 * @throws ParseException Throws Parse exception
	 * @throws IOException Throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getQueryKeyIndex(String accUrl) throws ParseException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", Constant.QUERY_KEY_INDEX);
		JSONObject param = new JSONObject();
		param.put("url", accUrl);
		obj.put("params", param);
		return httpConnection(obj, this.baseUrl);
		
	}

	/**
	 * Method used to Add credits to an account
	 * @param addCreditsArg  Add Credits Payload Parameters
	 * @param originSigner  Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return  Response Json which is sent by Acccumulate network
	 * @throws Exception  Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String addCredits(AddCreditsArg addCreditsArg, OriginSigner originSigner) throws Exception {
		return this.execute(new AddCredits(addCreditsArg),originSigner);

	}

	/**
	 * Method used to burn specified tokens from an account
	 * @param burnTokensArg Burn tokens Payload arguments
	 * @param originSigner  Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return  Response Json which is sent by Acccumulate network
	 * @throws Exception  Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String burnTokens(BurnTokensArg burnTokensArg, OriginSigner originSigner) throws Exception {
		return this.execute(new BurnTokens(burnTokensArg),originSigner);

	}

	/**
	 * Method used to Create a Data Account
	 * @param createDataAccountArg Create data account payload arguments
	 * @param originSigner  Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return  Response Json which is sent by Acccumulate network
	 * @throws Exception  Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String createDataAccount(CreateDataAccountArg createDataAccountArg, OriginSigner originSigner) throws Exception {
		return this.execute(new CreateDataAccount(createDataAccountArg),originSigner);
	}

	/**
	 * Method used to create an ADI
	 * @param createIdentityArg Create ADI Payload Arguments
	 * @param originSigner  Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return  Response Json which is sent by Acccumulate network
	 * @throws Exception  Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String createIdentity(CreateIdentityArg createIdentityArg, OriginSigner originSigner) throws Exception {
		return this.execute(new CreateIdentity(createIdentityArg),originSigner);
	}

	/**
	 * Method used to create Key book
	 * @param createKeyBookArg Create key book payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String createKeyBook(CreateKeyBookArg createKeyBookArg, OriginSigner originSigner) throws Exception {
		return this.execute(new CreateKeyBook(createKeyBookArg),originSigner);
	}

	/**
	 * Method used to create key page
	 * @param createKeyPageArg Create Key Page payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String createKeyPage(CreateKeyPageArg createKeyPageArg, OriginSigner originSigner) throws Exception {
		return this.execute(new CreateKeyPage(createKeyPageArg),originSigner);
	}

	/**
	 * Method used to create a token account
	 * @param createTokenAccountArg Create token account payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String createTokenAccount(CreateTokenAccountArg createTokenAccountArg, OriginSigner originSigner) throws Exception {
		return this.execute(new CreateTokenAccount(createTokenAccountArg),originSigner);
	}


	/**
	 * Method used to create Tokens
	 * @param createTokenArg Create token payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String createToken(CreateTokenArg createTokenArg, OriginSigner originSigner) throws Exception {
		return this.execute(new CreateToken(createTokenArg),originSigner);
	}


	/**
	 * Method used to Issue tokens to a specific account
	 * @param issueTokensArg Issue tokens Payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String issueTokens(IssueTokensArg issueTokensArg, OriginSigner originSigner) throws Exception {
		return this.execute(new IssueTokens(issueTokensArg),originSigner);
	}


	/**
	 * Method used to Send tokens to a specific account
	 * @param sendTokensArg Send tokens Payload Arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String sendToken(SendTokensArg sendTokensArg, OriginSigner originSigner) throws Exception {
		return this.execute(new SendTokens(sendTokensArg),originSigner);

	}


	/**
	 * Method used to update an existing Key page
	 * @param updateKeyPageArg Update Key page payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String updateKeyPage(UpdateKeyPageArg updateKeyPageArg, OriginSigner originSigner) throws Exception {
		return this.execute(new UpdateKeyPage(updateKeyPageArg),originSigner);
	}


	/**
	 * Method used to write Data
	 * @param writeDataArg Write Data Payload arguments
	 * @param originSigner Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return Response Json which is sent by Acccumulate network
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String writeData(WriteDataArg writeDataArg, OriginSigner originSigner) throws Exception {
		return this.execute(new WriteData(writeDataArg),originSigner);
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
