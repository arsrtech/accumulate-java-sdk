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
@JsonTypeName("QueryOptions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryOptions implements Marhallable {
	private boolean expand;
	private long height;
	private boolean scratch;
	private boolean prove;

    //
	public boolean getExpand() {
	    return expand;
	}
	public void setExpand(final boolean value) {
	    this.expand = value;
	}

	public QueryOptions expand(final boolean value) {
	    setExpand(value);
	    return this;
	}
	public long getHeight() {
	    return height;
	}
	public void setHeight(final long value) {
	    this.height = value;
	}

	public QueryOptions height(final long value) {
	    setHeight(value);
	    return this;
	}
	public boolean getScratch() {
	    return scratch;
	}
	public void setScratch(final boolean value) {
	    this.scratch = value;
	}

	public QueryOptions scratch(final boolean value) {
	    setScratch(value);
	    return this;
	}
	public boolean getProve() {
	    return prove;
	}
	public void setProve(final boolean value) {
	    this.prove = value;
	}

	public QueryOptions prove(final boolean value) {
	    setProve(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(!this.expand)) {
            marshaller.writeBool(1, this.expand);
        }
        if (!(this.height == 0)) {
            marshaller.writeUint(2, this.height);
        }
        if (!(!this.scratch)) {
            marshaller.writeBool(3, this.scratch);
        }
        if (!(!this.prove)) {
            marshaller.writeBool(4, this.prove);
        }
        return marshaller.array();
    }
}