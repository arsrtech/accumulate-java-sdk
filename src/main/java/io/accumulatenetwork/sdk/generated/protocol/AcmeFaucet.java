package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.TransactionBody;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: TransactionType
// UnionValue: AcmeFaucet

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("AcmeFaucet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcmeFaucet implements TransactionBody {
	public final TransactionType type = TransactionType.ACME_FAUCET;
	private Url url;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public AcmeFaucet url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public AcmeFaucet url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.url == null)) {
            marshaller.writeUrl(2, this.url);
        }
        return marshaller.array();
    }
}