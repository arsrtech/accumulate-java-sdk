package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Account;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: AccountType
// UnionValue: LiteDataAccount

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("LiteDataAccount")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LiteDataAccount implements Account {
	public final AccountType type = AccountType.LITE_DATA_ACCOUNT;
	private Url url;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public LiteDataAccount url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public LiteDataAccount url(final String value) {
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
