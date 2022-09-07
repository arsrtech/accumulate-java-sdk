package io.accumulatenetwork.sdk.generated.query;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ResponseKeyPageIndex")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseKeyPageIndex implements Marhallable {
	private Url authority;
	private Url signer;
	private long index;

    //
	public Url getAuthority() {
	    return authority;
	}
	public void setAuthority(final Url value) {
	    this.authority = value;
	}

	public ResponseKeyPageIndex authority(final Url value) {
	    setAuthority(value);
	    return this;
	}
	public ResponseKeyPageIndex authority(final String value) {
	    setAuthority(Url.parse(value));
	    return this;
	}
	public Url getSigner() {
	    return signer;
	}
	public void setSigner(final Url value) {
	    this.signer = value;
	}

	public ResponseKeyPageIndex signer(final Url value) {
	    setSigner(value);
	    return this;
	}
	public ResponseKeyPageIndex signer(final String value) {
	    setSigner(Url.parse(value));
	    return this;
	}
	public long getIndex() {
	    return index;
	}
	public void setIndex(final long value) {
	    this.index = value;
	}

	public ResponseKeyPageIndex index(final long value) {
	    setIndex(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.authority == null)) {
            marshaller.writeUrl(1, this.authority);
        }
        if (!(this.signer == null)) {
            marshaller.writeUrl(2, this.signer);
        }
        marshaller.writeUint(3, this.index);
        return marshaller.array();
    }
}
