package io.accumulatenetwork.sdk.api.v2;

import io.accumulatenetwork.sdk.protocol.TxID;

public class TransactionResult<T> {
    private final TxID txID;
    private final T result;

    public TransactionResult(final TxID txID, final T result) {
        this.txID = txID;
        this.result = result;
    }

    public TxID getTxID() {
        return txID;
    }

    public T getResult() {
        return result;
    }
}
