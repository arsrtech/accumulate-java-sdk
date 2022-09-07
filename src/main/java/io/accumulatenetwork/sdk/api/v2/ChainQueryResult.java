package io.accumulatenetwork.sdk.api.v2;


import io.accumulatenetwork.sdk.generated.apiv2.ChainQueryResponse;
import io.accumulatenetwork.sdk.protocol.QueryResponseType;

public class ChainQueryResult {
    private final ChainQueryResponse queryResponse;
    private final QueryResponseType queryResponseType;

    public ChainQueryResult(final ChainQueryResponse queryResponse, final QueryResponseType queryResponseType) {
        this.queryResponse = queryResponse;
        this.queryResponseType = queryResponseType;
    }

    public ChainQueryResponse getQueryResponse() {
        return queryResponse;
    }

    public QueryResponseType getQueryResponseType() {
        return queryResponseType;
    }
}
