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
// UnionValue: Identity

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ADI")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ADI implements Account {
	public final AccountType type = AccountType.IDENTITY;
	private Url url;
	private AccountAuth accountAuth;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public ADI url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public ADI url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}
	public AccountAuth getAccountAuth() {
	    return accountAuth;
	}
	public void setAccountAuth(final AccountAuth value) {
	    this.accountAuth = value;
	}

	public ADI accountAuth(final AccountAuth value) {
	    setAccountAuth(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.url == null)) {
            marshaller.writeUrl(2, this.url);
        }
        if (!(this.accountAuth == null)) {
            marshaller.writeValue(3, accountAuth);
        }
        return marshaller.array();
    }
}
