package io.accumulatenetwork.sdk.generated.apiv2;

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
@JsonTypeName("TokenSend")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenSend implements Marhallable {
	private Url from;
	private TokenDeposit[] to;

    //
	public Url getFrom() {
	    return from;
	}
	public void setFrom(final Url value) {
	    this.from = value;
	}

	public TokenSend from(final Url value) {
	    setFrom(value);
	    return this;
	}
	public TokenSend from(final String value) {
	    setFrom(Url.parse(value));
	    return this;
	}
	public TokenDeposit[] getTo() {
	    return to;
	}
	public void setTo(final TokenDeposit[] value) {
	    this.to = value;
	}

	public TokenSend to(final TokenDeposit[] value) {
	    setTo(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.from == null)) {
            marshaller.writeUrl(1, this.from);
        }
        if (!(this.to == null)) {
            marshaller.writeValue(2, to);
        }
        return marshaller.array();
    }
}
