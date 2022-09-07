package io.accumulatenetwork.sdk.generated.query;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ResponseTxHistory")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTxHistory implements Marhallable {
	private long start;
	private long end;
	private long total;
	private ResponseByTxId[] transactions;

    //
	public long getStart() {
	    return start;
	}
	public void setStart(final long value) {
	    this.start = value;
	}

	public ResponseTxHistory start(final long value) {
	    setStart(value);
	    return this;
	}
	public long getEnd() {
	    return end;
	}
	public void setEnd(final long value) {
	    this.end = value;
	}

	public ResponseTxHistory end(final long value) {
	    setEnd(value);
	    return this;
	}
	public long getTotal() {
	    return total;
	}
	public void setTotal(final long value) {
	    this.total = value;
	}

	public ResponseTxHistory total(final long value) {
	    setTotal(value);
	    return this;
	}
	public ResponseByTxId[] getTransactions() {
	    return transactions;
	}
	public void setTransactions(final ResponseByTxId[] value) {
	    this.transactions = value;
	}

	public ResponseTxHistory transactions(final ResponseByTxId[] value) {
	    setTransactions(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeUint(1, this.start);
        marshaller.writeUint(2, this.end);
        marshaller.writeUint(3, this.total);
        if (!(this.transactions == null)) {
            marshaller.writeValue(4, transactions);
        }
        return marshaller.array();
    }
}
