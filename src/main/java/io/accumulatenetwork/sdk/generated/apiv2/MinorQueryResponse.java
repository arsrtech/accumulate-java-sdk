package io.accumulatenetwork.sdk.generated.apiv2;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.Hex2DDeserializer;
import io.accumulatenetwork.sdk.support.serializers.Hex2DSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("MinorQueryResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinorQueryResponse implements Marhallable {
	private long blockIndex;
	private java.time.OffsetDateTime blockTime;
	private long txCount;
	private byte[][] txIds;
	private TransactionQueryResponse[] transactions;

    //
	public long getBlockIndex() {
	    return blockIndex;
	}
	public void setBlockIndex(final long value) {
	    this.blockIndex = value;
	}

	public MinorQueryResponse blockIndex(final long value) {
	    setBlockIndex(value);
	    return this;
	}
	public java.time.OffsetDateTime getBlockTime() {
	    return blockTime;
	}
	public void setBlockTime(final java.time.OffsetDateTime value) {
	    this.blockTime = value;
	}

	public MinorQueryResponse blockTime(final java.time.OffsetDateTime value) {
	    setBlockTime(value);
	    return this;
	}
	public long getTxCount() {
	    return txCount;
	}
	public void setTxCount(final long value) {
	    this.txCount = value;
	}

	public MinorQueryResponse txCount(final long value) {
	    setTxCount(value);
	    return this;
	}
	@JsonDeserialize(using = Hex2DDeserializer.class)
	public byte[][] getTxIds() {
	    return txIds;
	}
	@JsonSerialize(using = Hex2DSerializer.class)
	public void setTxIds(final byte[][] value) {
	    this.txIds = value;
	}

	public MinorQueryResponse txIds(final byte[][] value) {
	    setTxIds(value);
	    return this;
	}
	public TransactionQueryResponse[] getTransactions() {
	    return transactions;
	}
	public void setTransactions(final TransactionQueryResponse[] value) {
	    this.transactions = value;
	}

	public MinorQueryResponse transactions(final TransactionQueryResponse[] value) {
	    setTransactions(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.blockIndex == 0)) {
            marshaller.writeUint(1, this.blockIndex);
        }
        if (!(this.blockTime == null)) {
            marshaller.writeTime(2, this.blockTime);
        }
        if (!(this.txCount == 0)) {
            marshaller.writeUint(3, this.txCount);
        }
        if (!(this.txIds == null || this.txIds.length == 0)) {
            marshaller.writeBytes(4, this.txIds);
        }
        if (!(this.transactions == null)) {
            marshaller.writeValue(5, transactions);
        }
        return marshaller.array();
    }
}
