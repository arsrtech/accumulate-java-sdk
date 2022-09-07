package io.accumulatenetwork.sdk.api.v2;

import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;
import io.accumulatenetwork.sdk.generated.apiv2.*;
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
import io.accumulatenetwork.sdk.rpc.RPCException;
import io.accumulatenetwork.sdk.rpc.SyncRPCClient;
import io.accumulatenetwork.sdk.signing.SignersPreparer;
import io.accumulatenetwork.sdk.support.ResultReader;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AccumulateSyncApi extends AbstractAccumulateApi {

    private final SyncRPCClient rpcClient;

    public AccumulateSyncApi() {
        rpcClient = new SyncRPCClient();
    }

    public AccumulateSyncApi(final URI uri) {
        rpcClient = new SyncRPCClient(uri);
    }

    public TxID faucet(final Url url) {
        var txStatus = rpcClient.sendTx(new AcmeFaucet().url(url));
        return txStatus.getTxid();
    }

    public TransactionResult<AddCreditsResult> addCredits(final Principal principal, final AddCredits addCredits) {
        // TODO query Oracle for the user when empty?
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, addCredits));
        return new TransactionResult<>(txStatus.getTxID(), (AddCreditsResult) txStatus.getResult());
    }

    public TxID createIdentity(final Principal principal, final CreateIdentity createIdentity) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createIdentity));
        return txStatus.getTxID();
    }

    public TxID burnTokens(final Principal principal, final BurnTokens burnTokens) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, burnTokens));
        return txStatus.getTxID();
    }

    public TxID createDataAccount(final Principal principal, final CreateDataAccount createDataAccount) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createDataAccount));
        return txStatus.getTxID();
    }

    public TxID createTokenAccount(final Principal principal, final CreateTokenAccount createTokenAccount) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createTokenAccount));
        return txStatus.getTxID();
    }

    public TxID createLiteTokenAccount(final Principal principal, final CreateLiteTokenAccount createLiteTokenAccount) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createLiteTokenAccount));
        return txStatus.getTxID();
    }

    public TransactionResult<WriteDataResult> createLiteDataAccount(final Principal principal, final FactomEntry factomEntry) {

        final byte[] accountId = calculateFactomAccountId(factomEntry);
        final var writeDataTo = new WriteDataTo()
                .entry(new FactomDataEntryWrapper()
                        .factomDataEntry(new FactomDataEntry()
                                .accountId(accountId)
                                .extIds(factomExtRefsToByteArray(factomEntry.getExtRefs()))
                        ))
                .recipient(Url.parse(Hex.encodeHexString(accountId)));
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, writeDataTo));
        return new TransactionResult<>(txStatus.getTxID(), (WriteDataResult) txStatus.getResult());
    }

    public TxID createAccount(final Principal principal, final CreateToken createToken) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createToken));
        return txStatus.getTxID();
    }

    public TransactionResult<WriteDataResult> writeData(final Principal principal, final WriteData writeData) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, writeData));
        return new TransactionResult<>(txStatus.getTxID(), (WriteDataResult) txStatus.getResult());
    }

    public TransactionResult<WriteDataResult> writeDataTo(final Principal principal, final WriteDataTo writeDataTo) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, writeDataTo));
        return new TransactionResult<>(txStatus.getTxID(), (WriteDataResult) txStatus.getResult());
    }

    public TxID issueTokens(final Principal principal, final IssueTokens issueTokens) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, issueTokens));
        return txStatus.getTxID();
    }

    public TxID sendTokens(final Principal principal, final SendTokens sendTokens) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, sendTokens));
        return txStatus.getTxID();
    }

    public TxID createKeyBook(final Principal principal, final CreateKeyBook createKeyBook) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createKeyBook));
        return txStatus.getTxID();
    }

    public TxID createKeyPage(final Principal principal, final CreateKeyPage createKeyPage) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, createKeyPage));
        return txStatus.getTxID();
    }

    public TxID updateKeyPage(final Principal principal, final UpdateKeyPage updateKeyPage) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, updateKeyPage));
        return txStatus.getTxID();
    }

    public TxID updateKey(final Principal principal, final UpdateKey updateKey) {
        final var txStatus = rpcClient.sendTx(envelopeBuilder(principal, updateKey));
        return txStatus.getTxID();
    }


    public TransactionQueryResult getTx(final TxID txId) {
        return getTx(txId.getHash());
    }

    public TransactionQueryResult getTx(final byte[] txId) {
        return getTx(new TxnQuery().txid(txId));
    }

    public TransactionQueryResult getTx(final TxnQuery txnQuery) {
        try {
            final var response = rpcClient.send(txnQuery);
            final TransactionQueryResponse txQueryResponse = ResultReader.readValue(response.getResultNode(), TransactionQueryResponse.class);
            return new TransactionQueryResult(txQueryResponse, ResultReader.getTransactionType(txQueryResponse.getData()));
        } catch (final RPCException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionResultItem> getTxHistory(final TxHistoryQuery query) {
        try {
            final var response = rpcClient.send(query);
            final MultiResponse multiResponse = ResultReader.readValue(response.getResultNode(), MultiResponse.class);
            final List<TransactionResultItem> result = new ArrayList<>((int) multiResponse.getCount());
            for (final JsonNode item : multiResponse.getItems()) {
                result.add(new TransactionResultItem(item.get("data"), ResultReader.getTransactionType(item)));
            }
            return result;
        } catch (final RPCException e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseKeyPageIndex queryKeyIndex(final KeyPageIndexQuery query) {
        try {
            final var response = rpcClient.send(query);
            final ChainQueryResponse chainQueryResponse = ResultReader.readValue(response.getResultNode(), ChainQueryResponse.class);
            final QueryResponseType queryResponseType = QueryResponseType.fromJsonNode(response.getResultNode());
            if (queryResponseType != QueryResponseType.KEY_PAGE_INDEX) {
                throw new RuntimeException(String.format("Invalid query response type, expected %s got %s",
                        QueryResponseType.KEY_PAGE_INDEX.getResponseType(), queryResponseType.getResponseType()));
            }
            return ResultReader.readValue(chainQueryResponse.getData(), ResponseKeyPageIndex.class);
        } catch (final RPCException e) {
            throw new RuntimeException(e);
        }
    }

    public DataEntryQueryResponse queryData(final DataEntryQuery query) {
        try {
            final var response = rpcClient.send(query);
            return ResultReader.readValue(response.getResultNode(), DataEntryQueryResponse.class);
        } catch (final RPCException e) {
            throw new RuntimeException(e);
        }
    }

    public ChainQueryResponse queryUrl(final GeneralQuery generalQuery) {
        try {
            final var response = rpcClient.send(generalQuery);
            return ResultReader.readValue(response.getResultNode(), ChainQueryResponse.class);
        } catch (final RPCException e) {
            throw new RuntimeException(e);
        }
    }

    private EnvelopeBuilder envelopeBuilder(final Principal principal, final TransactionBody txBody) {
        Url signerUrl;
        final Url principalUrl = principal.getAccount().getUrl();
        if (principal instanceof LiteAccount) {
            signerUrl = principalUrl.rootUrl();
        } else {
            final var responseKeyPageIndex = queryKeyIndex(new KeyPageIndexQuery()
                    .url(principalUrl)
                    .key(principal.getSignatureKeyPair().getPublicKey()));
            signerUrl = responseKeyPageIndex.getSigner();
        }

        final SignersPreparer signersPreparer = new SignersPreparer(principal.getSignatureKeyPair(), signerUrl);
        return new EnvelopeBuilder(principalUrl, signersPreparer, txBody);
    }
}
