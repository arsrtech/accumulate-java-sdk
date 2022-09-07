package io.accumulatenetwork.sdk.api.v2;


import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.generated.protocol.TransactionType;

public class TransactionResultItem {
    private final JsonNode data;
    private final TransactionType txType;

    TransactionResultItem(final JsonNode data, final TransactionType txType) {
        this.data = data;
        this.txType = txType;
    }

    public JsonNode getData() {
        return data;
    }

    public TransactionType getTxType() {
        return txType;
    }
}
