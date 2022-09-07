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
// UnionValue: KeyBook

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("KeyBook")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyBook implements Account {
	public final AccountType type = AccountType.KEY_BOOK;
	private Url url;
	private BookType bookType;
	private AccountAuth accountAuth;
	private long pageCount;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public KeyBook url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public KeyBook url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}
	public BookType getBookType() {
	    return bookType;
	}
	public void setBookType(final BookType value) {
	    this.bookType = value;
	}

	public KeyBook bookType(final BookType value) {
	    setBookType(value);
	    return this;
	}
	public AccountAuth getAccountAuth() {
	    return accountAuth;
	}
	public void setAccountAuth(final AccountAuth value) {
	    this.accountAuth = value;
	}

	public KeyBook accountAuth(final AccountAuth value) {
	    setAccountAuth(value);
	    return this;
	}
	public long getPageCount() {
	    return pageCount;
	}
	public void setPageCount(final long value) {
	    this.pageCount = value;
	}

	public KeyBook pageCount(final long value) {
	    setPageCount(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.url == null)) {
            marshaller.writeUrl(2, this.url);
        }
        if (!(this.bookType == null)) {
            marshaller.writeValue(3, bookType);
        }
        if (!(this.accountAuth == null)) {
            marshaller.writeValue(4, accountAuth);
        }
        if (!(this.pageCount == 0)) {
            marshaller.writeUint(5, this.pageCount);
        }
        return marshaller.array();
    }
}