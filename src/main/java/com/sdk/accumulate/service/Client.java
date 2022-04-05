package com.sdk.accumulate.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.sdk.accumulate.model.*;
import com.sdk.accumulate.query.*;

public class Client {

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
	 * @return Returns RPC Call Response
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
	 * @param params Query API params
	 * @param method Query Method
	 * @return Returns RPC Call Response
	 * @throws Exception Throws Exception In case of RPC call failure or Parser failure
	 */
	private String execute_query(BaseQueryPrams params,String method) throws Exception {
		return RPCClient.client(this.baseUrl,params,method);
	}

	/**
	 * Method used to query about a lite account. It will return the details of a specific account
	 * @param accUrl  Account URL
	 * @return Returns RPC Call Response
	 * {
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "type": "liteTokenAccount",
	 *         "merkleState": {
	 *             "count": 1,
	 *             "roots": [
	 *                 "c7e59bfea5029963ee07c27a09e5e7467e30cd4ebc1822e16ebbb173a8ff685c"
	 *             ]
	 *         },
	 *         "data": {
	 *             "type": "liteTokenAccount",
	 *             "url": "acc://5fd54e898c2c60e9757d2cd36f3a14b6df895b237690afb1/ACME",
	 *             "keyBook": "0000000000000000000000000000000000000000000000000000000000000000",
	 *             "managerKeyBook": "",
	 *             "tokenUrl": "acc://ACME",
	 *             "balance": 1000000000,
	 *             "txCount": 1,
	 *             "creditBalance": 0
	 *         }
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */
	public String getQuery(String accUrl) throws Exception{
		QueryLiteAccount queryLiteAccount = new QueryLiteAccount(accUrl);
		return execute_query(queryLiteAccount, Constant.QUERY);
		
	}

	/**
	 * Method used to get the transaction history based on account
	 * @param accUrl Account URL
	 * @param count Transaction history count
	 * @return Returns RPC Call Response
	 * {
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "type": "txHistory",
	 *         "items": [
	 *             {
	 *                 "type": "syntheticDepositTokens",
	 *                 "data": {
	 *                     "cause": "395caba8d27056614abf991ab40a1b53a8b8babc4919d34ababa24f603f8a1a3",
	 *                     "token": "ACME",
	 *                     "amount": "1000000000"
	 *                 },
	 *                 "origin": "acc://09bca3fcd7e28434f4fb5a3c7317d64e955acbc102e45df5/ACME",
	 *                 "sponsor": "acc://09bca3fcd7e28434f4fb5a3c7317d64e955acbc102e45df5/ACME",
	 *                 "keyPage": {
	 *                     "height": 1,
	 *                     "index": 0
	 *                 },
	 *                 "transactionHash": "2bd403d477d70b87e35e88b0aa316242d89aa41db5b5dea6df5a1c470e424671",
	 *                 "txid": "2bd403d477d70b87e35e88b0aa316242d89aa41db5b5dea6df5a1c470e424671",
	 *                 "signatures": [
	 *                     {
	 *                         "Nonce": 390,
	 *                         "PublicKey": "Kdjx5D4pOOIrd/gNV3S+21DF5g93dR6IZ1ZfwfDi94g=",
	 *                         "Signature": "tyKHXcPWzJBwkNJOTljvYmRQdzi9eJROgfIVJkcIJ6+HnmrYZnTtCKz5jE/Q5mKfvqiquAfEvUWw4nXHjpEMAA=="
	 *                     }
	 *                 ],
	 *                 "status": {
	 *                     "delivered": true,
	 *                     "result": {}
	 *                 }
	 *             },
	 *             {
	 *                 "type": "syntheticDepositTokens",
	 *                 "data": {
	 *                     "cause": "4d253e95399ccbd6b7044d1fff85a6a7d6dd83ab209cdd25a416dd2c70c21e9d",
	 *                     "token": "ACME",
	 *                     "amount": "1000000000"
	 *                 },
	 *                 "origin": "acc://09bca3fcd7e28434f4fb5a3c7317d64e955acbc102e45df5/ACME",
	 *                 "sponsor": "acc://09bca3fcd7e28434f4fb5a3c7317d64e955acbc102e45df5/ACME",
	 *                 "keyPage": {
	 *                     "height": 1,
	 *                     "index": 0
	 *                 },
	 *                 "transactionHash": "bc573897d25828887b393a54ba8378d6af20298738dc909af4fb86c553689c9c",
	 *                 "txid": "bc573897d25828887b393a54ba8378d6af20298738dc909af4fb86c553689c9c",
	 *                 "signatures": [
	 *                     {
	 *                         "Nonce": 516,
	 *                         "PublicKey": "Kdjx5D4pOOIrd/gNV3S+21DF5g93dR6IZ1ZfwfDi94g=",
	 *                         "Signature": "y02cNToZnoqabkIY8V2v3UjQvdh47JNQzjlFHpsdYVsFQUtYIWZDJKzS8nahTcTfdoPH0SR4dEZ373W+AeAiAQ=="
	 *                     }
	 *                 ],
	 *                 "status": {
	 *                     "delivered": true,
	 *                     "result": {}
	 *                 }
	 *             }
	 *         ],
	 *         "start": 0,
	 *         "count": 2,
	 *         "total": 40
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */

	public String getTransactionHistory(String accUrl, int count) throws Exception {
		QueryTransactionHistory queryTransactionHistory = new QueryTransactionHistory();
		queryTransactionHistory.setCount(count);
		queryTransactionHistory.setUrl(accUrl);
		return execute_query(queryTransactionHistory, Constant.QUERY_TX_HISTORY);
		
	}

	/**
	 * Method used to get the transaction details based on transaction ID
	 * @param txid Transaction Reference ID
	 * @return Returns RPC Call Response
	 * {
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "type": "syntheticDepositTokens",
	 *         "data": {
	 *             "cause": "395caba8d27056614abf991ab40a1b53a8b8babc4919d34ababa24f603f8a1a3",
	 *             "token": "ACME",
	 *             "amount": "1000000000"
	 *         },
	 *         "origin": "acc://09bca3fcd7e28434f4fb5a3c7317d64e955acbc102e45df5/ACME",
	 *         "sponsor": "acc://09bca3fcd7e28434f4fb5a3c7317d64e955acbc102e45df5/ACME",
	 *         "keyPage": {
	 *             "height": 1,
	 *             "index": 0
	 *         },
	 *         "transactionHash": "2bd403d477d70b87e35e88b0aa316242d89aa41db5b5dea6df5a1c470e424671",
	 *         "txid": "2bd403d477d70b87e35e88b0aa316242d89aa41db5b5dea6df5a1c470e424671",
	 *         "signatures": [
	 *             {
	 *                 "Nonce": 390,
	 *                 "PublicKey": "Kdjx5D4pOOIrd/gNV3S+21DF5g93dR6IZ1ZfwfDi94g=",
	 *                 "Signature": "tyKHXcPWzJBwkNJOTljvYmRQdzi9eJROgfIVJkcIJ6+HnmrYZnTtCKz5jE/Q5mKfvqiquAfEvUWw4nXHjpEMAA=="
	 *             }
	 *         ],
	 *         "status": {
	 *             "delivered": true,
	 *             "result": {}
	 *         }
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */
	public String getTransaction(String txid) throws Exception {
		QueryTransaction queryTransaction = new QueryTransaction(txid);
		return execute_query(queryTransaction, Constant.QUERY_TRANSACTION);


	}

	/**
	 * Method used to get the transaction based on chain ID
	 * @param chainId Transaction chain ID
	 * @return Returns RPC Call Response
	{
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "type": "keyPage",
	 *         "merkleState": {
	 *             "count": 1,
	 *             "roots": [
	 *                 "cb0d7ccf124390693628c86cbb31cba64cee6c56b9086403f22c805b6bdf06d8"
	 *             ]
	 *         },
	 *         "data": {
	 *             "type": "keyPage",
	 *             "url": "acc://kg1/prishth",
	 *             "keyBook": "4b6fdf9d3e4ca631a0d1ca92eef528b7cfcd16a953efbe5e70811cd3841e88da",
	 *             "managerKeyBook": "",
	 *             "creditBalance": 0,
	 *             "keys": [
	 *                 {
	 *                     "publicKey": "57ddf8f09ddaaf28656fcca6ef1d4bb028c02ed31584c36df1e0ffcacc14d90c"
	 *                 }
	 *             ]
	 *         }
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */
	public String getQueryChain(String chainId) throws Exception{
		QueryChain queryChain = new QueryChain(chainId);
		return execute_query(queryChain, Constant.QUERY_CHAIN);
	}

	/**
	 * Method used to query the Data based on Account
	 * @param accUrl Account URL
	 * @return Returns RPC Call Response
	 * {
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "type": "dataEntry",
	 *         "merkleState": {
	 *             "count": 2,
	 *             "roots": [
	 *                 null,
	 *                 "87c5cc4a4f86accac328b5773c3e6fe29e8af23cbaa5a14f154011e2e71af4d2"
	 *             ]
	 *         },
	 *         "data": {
	 *             "entryHash": "b45fa53718dbc5bf31f2f6134d1ff84fe22b3760face9c2ab012fd66d16d1808",
	 *             "entry": {
	 *                 "data": "61646974776f64617461"
	 *             }
	 *         }
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */
	public String getQueryData(String accUrl) throws Exception {
		QueryData queryData = new QueryData(accUrl);
		return execute_query(queryData, Constant.QUERY_DATA);
	}

	/**
	 * Method used to Get faucet for an account. It also activates an account with the network
	 * @param accUrl Account URL
	 * @return Returns RPC Call Response
	 * {
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "hash": "be347539c19cb0d5e1b396b4142a637ff94aec795e6e81442d37fe73469bc5dd",
	 *         "message": "CheckTx",
	 *         "txid": "7ec47e5a48e7b6e38040748885a9702ef1949b45325ac5eafd0f616457e92cda"
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */

	public String getFaucet(String accUrl) throws Exception {
		GetFaucet getFaucet = new GetFaucet(accUrl);
		return execute_query(getFaucet, Constant.FAUCET);
		
	}

	/**
	 * Method used query the Key index based on Account
	 * @param accUrl Account URL
	 * @return Returns RPC Call Response
	 * {
	 *     "jsonrpc": "2.0",
	 *     "result": {
	 *         "type": "key-page-index",
	 *         "data": {
	 *             "keyBook": "acc://adione/book0",
	 *             "keyPage": "acc://adione/page0",
	 *             "index": 0
	 *         }
	 *     },
	 *     "id": 0
	 * }
	 * @throws IOException Throws IOException
	 */
	public String getQueryKeyIndex(String accUrl) throws Exception {
		QueryKeyIndex queryKeyIndex = new QueryKeyIndex(accUrl);
		return execute_query(queryKeyIndex, Constant.QUERY_KEY_INDEX);

	}

	/**
	 * Method used to Add credits to an account
	 * @param addCreditsArg  Add Credits Payload Parameters
	 * @param originSigner  Signer of the transaction (That maybe a lite account or an ADI or a Data account or a Token Account)
	 * @return  Response Json which is sent by Acccumulate network
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "1e6c539958603c0281df4831d2e7633db4fe32d0678c6bf60246d3f0597dbcdb",
	 *     "txid": "1e6c539958603c0281df4831d2e7633db4fe32d0678c6bf60246d3f0597dbcdb",
	 *     "envelopeHash": "6b776939deaf76f0a6568565e89c71d063a8a608c5437ce733226f344a55848e",
	 *     "simpleHash": "7adda8c51d3edc39e6bb4abe3392ac528203387ed63290e338001fbe3b8b4ba1",
	 *     "hash": "7adda8c51d3edc39e6bb4abe3392ac528203387ed63290e338001fbe3b8b4ba1"
	 *   },
	 *   "id": 1647927131154
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "ecaa4ee1711818d556dac1d19f11210b9f485e82c32d448a9ced7fcdb5f3b853",
	 *     "txid": "ecaa4ee1711818d556dac1d19f11210b9f485e82c32d448a9ced7fcdb5f3b853",
	 *     "envelopeHash": "3d88e4cb2f4b67867b6d7bd539044622031c0619e309cd8fa7758bc11bef9556",
	 *     "simpleHash": "4206f202d01ea0a71c515cad863bd28eaa6e38d7bc05337266de2c3407d58bf2",
	 *     "hash": "4206f202d01ea0a71c515cad863bd28eaa6e38d7bc05337266de2c3407d58bf2"
	 *   },
	 *   "id": 1647927356568
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
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
	 * {
	 *   "jsonrpc": "2.0",
	 *   "result": {
	 *     "transactionHash": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "txid": "0348c88f3ea52021625ef872ff2be961d39b4b4d78fd9c76070f6a08da15fbbf",
	 *     "envelopeHash": "4c129f458aa27df0f0d990b077ba213f7ba6767466e50fb02e48ea135c050ebe",
	 *     "simpleHash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401",
	 *     "hash": "0c7c5ff7c865afb7c488eba3e3fa389f79bd4a956c49b026257ea9b31bbe2401"
	 *   },
	 *   "id": 1647927432541
	 * }
	 * @throws Exception Throws Exception in the case of RPC call failure or Parse failure
	 */
	public String writeData(WriteDataArg writeDataArg, OriginSigner originSigner) throws Exception {
		return this.execute(new WriteData(writeDataArg),originSigner);
	}
}
