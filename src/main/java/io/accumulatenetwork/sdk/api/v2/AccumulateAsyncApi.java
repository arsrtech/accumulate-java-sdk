package io.accumulatenetwork.sdk.api.v2;

import io.accumulatenetwork.sdk.commons.codec.binary.Hex;
import io.accumulatenetwork.sdk.generated.apiv2.ChainQueryResponse;
import io.accumulatenetwork.sdk.generated.apiv2.KeyPageIndexQuery;
import io.accumulatenetwork.sdk.generated.apiv2.TransactionQueryResponse;
import io.accumulatenetwork.sdk.generated.apiv2.TxnQuery;
import io.accumulatenetwork.sdk.generated.protocol.AcmeFaucet;
import io.accumulatenetwork.sdk.generated.protocol.AddCredits;
import io.accumulatenetwork.sdk.generated.protocol.AddCreditsResult;
import io.accumulatenetwork.sdk.generated.protocol.BurnTokens;
import io.accumulatenetwork.sdk.generated.protocol.CreateDataAccount;
import io.accumulatenetwork.sdk.generated.protocol.CreateIdentity;
import io.accumulatenetwork.sdk.generated.protocol.CreateKeyBook;
import io.accumulatenetwork.sdk.generated.protocol.CreateKeyPage;
import io.accumulatenetwork.sdk.generated.protocol.CreateLiteTokenAccount;
import io.accumulatenetwork.sdk.generated.protocol.CreateToken;
import io.accumulatenetwork.sdk.generated.protocol.CreateTokenAccount;
import io.accumulatenetwork.sdk.generated.protocol.EmptyResult;
import io.accumulatenetwork.sdk.generated.protocol.FactomDataEntry;
import io.accumulatenetwork.sdk.generated.protocol.FactomDataEntryWrapper;
import io.accumulatenetwork.sdk.generated.protocol.IssueTokens;
import io.accumulatenetwork.sdk.generated.protocol.SendTokens;
import io.accumulatenetwork.sdk.generated.protocol.UpdateKey;
import io.accumulatenetwork.sdk.generated.protocol.UpdateKeyPage;
import io.accumulatenetwork.sdk.generated.protocol.WriteData;
import io.accumulatenetwork.sdk.generated.protocol.WriteDataResult;
import io.accumulatenetwork.sdk.generated.protocol.WriteDataTo;
import io.accumulatenetwork.sdk.generated.query.ResponseKeyPageIndex;
import io.accumulatenetwork.sdk.protocol.EnvelopeBuilder;
import io.accumulatenetwork.sdk.protocol.FactomEntry;
import io.accumulatenetwork.sdk.protocol.LiteAccount;
import io.accumulatenetwork.sdk.protocol.Principal;
import io.accumulatenetwork.sdk.protocol.QueryResponseType;
import io.accumulatenetwork.sdk.protocol.TransactionBody;
import io.accumulatenetwork.sdk.protocol.TxID;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.rpc.AsyncRPCClient;
import io.accumulatenetwork.sdk.signing.SignersPreparer;
import io.accumulatenetwork.sdk.support.ResultReader;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class AccumulateAsyncApi extends AbstractAccumulateApi {

    private final AsyncRPCClient rpcClient;

    public AccumulateAsyncApi() {
        rpcClient = new AsyncRPCClient();
    }

    public AccumulateAsyncApi(final URI uri) {
        rpcClient = new AsyncRPCClient(uri);
    }

    public CompletableFuture<TransactionResult<EmptyResult>> faucet(final Url url) {
        return rpcClient.sendTx(new AcmeFaucet().url(url))
                .thenApply(txResponse -> new TransactionResult<>(txResponse.getTxid(), new EmptyResult()));
    }


    public CompletableFuture<TransactionResult<AddCreditsResult>> addCredits(final Principal principal, final AddCredits addCredits) {
        return envelopeBuilder(principal, addCredits)
                .thenCompose(envelopeBuilder -> rpcClient.sendTx(envelopeBuilder)
                        .thenApply(txStatus -> new TransactionResult<>(txStatus.getTxID(), (AddCreditsResult) txStatus.getResult())));
    }

    public CompletableFuture<TransactionResult<EmptyResult>> createIdentity(final Principal principal, final CreateIdentity createIdentity) {
        return sendTxNoResult(principal, createIdentity);
    }


    public CompletableFuture<TransactionResult<EmptyResult>> burnTokens(final Principal principal, final BurnTokens burnTokens) {
        return sendTxNoResult(principal, burnTokens);
    }

    public CompletableFuture<TransactionResult<EmptyResult>> createDataAccount(final Principal principal, final CreateDataAccount createDataAccount) {
        return sendTxNoResult(principal, createDataAccount);
    }

    public CompletableFuture<TxID> createTokenAccount(final Principal principal, final CreateTokenAccount createTokenAccount) {
        return sendTxNoResult(principal, createTokenAccount)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TxID> createLiteTokenAccount(final Principal principal, final CreateLiteTokenAccount createLiteTokenAccount) {
        return sendTxNoResult(principal, createLiteTokenAccount)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TransactionResult<WriteDataResult>> createLiteDataAccount(final Principal principal, final FactomEntry factomEntry) {
        final byte[] accountId = calculateFactomAccountId(factomEntry);
        final var writeDataTo = new WriteDataTo()
                .entry(new FactomDataEntryWrapper()
                        .factomDataEntry(new FactomDataEntry()
                                .accountId(accountId)
                                .extIds(factomExtRefsToByteArray(factomEntry.getExtRefs()))
                                .data(factomEntry.getData())
                        ))
                .recipient(Url.parse(Hex.encodeHexString(accountId)));
        return envelopeBuilder(principal, writeDataTo)
                .thenCompose(envelopeBuilder -> rpcClient.sendTx(envelopeBuilder)
                        .thenApply(txStatus -> new TransactionResult<>(txStatus.getTxID(), (WriteDataResult) txStatus.getResult())));

    }

    public CompletableFuture<TxID> createAccount(final Principal principal, final CreateToken createToken) {
        return sendTxNoResult(principal, createToken)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TransactionResult<WriteDataResult>> writeData(final Principal principal, final WriteData writeData) {
        return envelopeBuilder(principal, writeData)
                .thenCompose(envelopeBuilder -> rpcClient.sendTx(envelopeBuilder)
                        .thenApply(txStatus -> new TransactionResult<>(txStatus.getTxID(), (WriteDataResult) txStatus.getResult())));
    }

    public CompletableFuture<TransactionResult<WriteDataResult>> writeDataTo(final Principal principal, final WriteDataTo writeDataTo) {
        return envelopeBuilder(principal, writeDataTo)
                .thenCompose(envelopeBuilder -> rpcClient.sendTx(envelopeBuilder)
                        .thenApply(txStatus -> new TransactionResult<>(txStatus.getTxID(), (WriteDataResult) txStatus.getResult())));
    }

    public CompletableFuture<TxID> issueTokens(final Principal principal, final IssueTokens issueTokens) {
        return sendTxNoResult(principal, issueTokens)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TxID> sendTokens(final Principal principal, final SendTokens sendTokens) {
        return sendTxNoResult(principal, sendTokens)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TxID> createKeyBook(final Principal principal, final CreateKeyBook createKeyBook) {
        return sendTxNoResult(principal, createKeyBook)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TxID> createKeyPage(final Principal principal, final CreateKeyPage createKeyPage) {
        return sendTxNoResult(principal, createKeyPage)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TxID> updateKeyPage(final Principal principal, final UpdateKeyPage updateKeyPage) {
        return sendTxNoResult(principal, updateKeyPage)
                .thenApply(TransactionResult::getTxID);
    }

    public CompletableFuture<TxID> updateKey(final Principal principal, final UpdateKey updateKey) {
        return sendTxNoResult(principal, updateKey)
                .thenApply(TransactionResult::getTxID);
    }

    private CompletableFuture<TransactionResult<EmptyResult>> sendTxNoResult(final Principal principal, final TransactionBody transactionBody) {
        return envelopeBuilder(principal, transactionBody)
                .thenCompose(envelopeBuilder -> rpcClient.sendTx(envelopeBuilder)
                        .thenApply(txResponse -> new TransactionResult<>(txResponse.getTxID(), null)));
    }

    public CompletableFuture<TransactionQueryResult> getTx(final TxID txId) {
        return getTx(txId.getHash());
    }

    public CompletableFuture<TransactionQueryResult> getTx(final byte[] txId) {
        return getTx(new TxnQuery().txid(txId));
    }

    public CompletableFuture<TransactionQueryResult> getTx(final TxnQuery txnQuery) {
        return rpcClient.send(txnQuery)
                .thenApply(rpcResponse -> {
                    final TransactionQueryResponse txQueryResponse = ResultReader.readValue(rpcResponse.getResultNode(), TransactionQueryResponse.class);
                    return new TransactionQueryResult(txQueryResponse, ResultReader.getTransactionType(txQueryResponse.getData()));
                });
    }

    public CompletableFuture<ResponseKeyPageIndex> queryKeyIndex(final KeyPageIndexQuery query) {
        return rpcClient.send(query).thenApply(rpcResponse -> {
            final ChainQueryResponse chainQueryResponse = ResultReader.readValue(rpcResponse.getResultNode(), ChainQueryResponse.class);
            final QueryResponseType queryResponseType = QueryResponseType.fromJsonNode(rpcResponse.getResultNode());
            if (queryResponseType != QueryResponseType.KEY_PAGE_INDEX) {
                throw new RuntimeException(String.format("Invalid query response type, expected %s got %s",
                        QueryResponseType.KEY_PAGE_INDEX.getResponseType(), queryResponseType.getResponseType()));
            }
            return ResultReader.readValue(chainQueryResponse.getData(), ResponseKeyPageIndex.class);
        });
    }

    private CompletableFuture<EnvelopeBuilder> envelopeBuilder(final Principal principal, final TransactionBody txBody) {
        final Url principalUrl = principal.getAccount().getUrl();
        if (principal instanceof LiteAccount) {
            try {
                final SignersPreparer signersPreparer = new SignersPreparer(principal.getSignatureKeyPair(), principalUrl);
                return CompletableFuture.completedFuture(new EnvelopeBuilder(principalUrl, signersPreparer, txBody));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return queryKeyIndex(new KeyPageIndexQuery()
                    .url(principalUrl)
                    .key(principal.getSignatureKeyPair().getPublicKey()))
                    .thenApply(result -> {
                        final SignersPreparer signersPreparer = new SignersPreparer(principal.getSignatureKeyPair(), result.getSigner());
                        return new EnvelopeBuilder(principalUrl, signersPreparer, txBody);
                    });
        }
    }
}
