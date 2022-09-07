package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Signature;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: SignatureType
// UnionValue: Remote

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("RemoteSignature")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteSignature implements Signature {
	public final SignatureType type = SignatureType.REMOTE;
	private Url destination;
	private Signature signature;

    //
	public Url getDestination() {
	    return destination;
	}
	public void setDestination(final Url value) {
	    this.destination = value;
	}

	public RemoteSignature destination(final Url value) {
	    setDestination(value);
	    return this;
	}
	public RemoteSignature destination(final String value) {
	    setDestination(Url.parse(value));
	    return this;
	}
	public Signature getSignature() {
	    return signature;
	}
	public void setSignature(final Signature value) {
	    this.signature = value;
	}

	public RemoteSignature signature(final Signature value) {
	    setSignature(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.destination == null)) {
            marshaller.writeUrl(2, this.destination);
        }
        if (!(this.signature == null)) {
            marshaller.writeValue(3, signature);
        }
        return marshaller.array();
    }
}