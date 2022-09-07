package io.accumulatenetwork.sdk.generated.apiv2;

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
@JsonTypeName("QueryPagination")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryPagination implements Marhallable {
	private long start;
	private long count;

    //
	public long getStart() {
	    return start;
	}
	public void setStart(final long value) {
	    this.start = value;
	}

	public QueryPagination start(final long value) {
	    setStart(value);
	    return this;
	}
	public long getCount() {
	    return count;
	}
	public void setCount(final long value) {
	    this.count = value;
	}

	public QueryPagination count(final long value) {
	    setCount(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.start == 0)) {
            marshaller.writeUint(1, this.start);
        }
        if (!(this.count == 0)) {
            marshaller.writeUint(2, this.count);
        }
        return marshaller.array();
    }
}