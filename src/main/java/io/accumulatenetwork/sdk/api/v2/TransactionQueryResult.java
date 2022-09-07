package io.accumulatenetwork.sdk.api.v2;


import io.accumulatenetwork.sdk.generated.apiv2.TransactionQueryResponse;
import io.accumulatenetwork.sdk.generated.protocol.TransactionType;

public class TransactionQueryResult {
    private final TransactionQueryResponse queryResponse;
    private final TransactionType txType;

    public TransactionQueryResult(final TransactionQueryResponse queryResponse, final TransactionType txType) {
        this.queryResponse = queryResponse;
        this.txType = txType;
    }

    public TransactionQueryResponse getQueryResponse() {
        return queryResponse;
    }

    public TransactionType getTxType() {
        return txType;
    }
}
