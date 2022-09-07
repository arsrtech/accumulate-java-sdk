package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Account;
import io.accumulatenetwork.sdk.protocol.AllowedTransactions;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: AccountType
// UnionValue: KeyPage

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("KeyPage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyPage implements Account {
	public final AccountType type = AccountType.KEY_PAGE;
	private Url url;
	private long creditBalance;
	private long acceptThreshold;
	private long rejectThreshold;
	private long responseThreshold;
	private long blockThreshold;
	private long version;
	private KeySpec[] keys;
	private AllowedTransactions transactionBlacklist;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public KeyPage url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public KeyPage url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}
	public long getCreditBalance() {
	    return creditBalance;
	}
	public void setCreditBalance(final long value) {
	    this.creditBalance = value;
	}

	public KeyPage creditBalance(final long value) {
	    setCreditBalance(value);
	    return this;
	}
	public long getAcceptThreshold() {
	    return acceptThreshold;
	}
	public void setAcceptThreshold(final long value) {
	    this.acceptThreshold = value;
	}

	public KeyPage acceptThreshold(final long value) {
	    setAcceptThreshold(value);
	    return this;
	}
	public long getRejectThreshold() {
	    return rejectThreshold;
	}
	public void setRejectThreshold(final long value) {
	    this.rejectThreshold = value;
	}

	public KeyPage rejectThreshold(final long value) {
	    setRejectThreshold(value);
	    return this;
	}
	public long getResponseThreshold() {
	    return responseThreshold;
	}
	public void setResponseThreshold(final long value) {
	    this.responseThreshold = value;
	}

	public KeyPage responseThreshold(final long value) {
	    setResponseThreshold(value);
	    return this;
	}
	public long getBlockThreshold() {
	    return blockThreshold;
	}
	public void setBlockThreshold(final long value) {
	    this.blockThreshold = value;
	}

	public KeyPage blockThreshold(final long value) {
	    setBlockThreshold(value);
	    return this;
	}
	public long getVersion() {
	    return version;
	}
	public void setVersion(final long value) {
	    this.version = value;
	}

	public KeyPage version(final long value) {
	    setVersion(value);
	    return this;
	}
	public KeySpec[] getKeys() {
	    return keys;
	}
	public void setKeys(final KeySpec[] value) {
	    this.keys = value;
	}

	public KeyPage keys(final KeySpec[] value) {
	    setKeys(value);
	    return this;
	}
	public AllowedTransactions getTransactionBlacklist() {
	    return transactionBlacklist;
	}
	public void setTransactionBlacklist(final AllowedTransactions value) {
	    this.transactionBlacklist = value;
	}

	public KeyPage transactionBlacklist(final AllowedTransactions value) {
	    setTransactionBlacklist(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.url == null)) {
            marshaller.writeUrl(2, this.url);
        }
        if (!(this.creditBalance == 0)) {
            marshaller.writeUint(3, this.creditBalance);
        }
        if (!(this.acceptThreshold == 0)) {
            marshaller.writeUint(4, this.acceptThreshold);
        }
        if (!(this.rejectThreshold == 0)) {
            marshaller.writeUint(5, this.rejectThreshold);
        }
        if (!(this.responseThreshold == 0)) {
            marshaller.writeUint(6, this.responseThreshold);
        }
        if (!(this.blockThreshold == 0)) {
            marshaller.writeUint(7, this.blockThreshold);
        }
        if (!(this.version == 0)) {
            marshaller.writeUint(8, this.version);
        }
        if (!(this.keys == null)) {
            marshaller.writeValue(9, keys);
        }
        if (!(this.transactionBlacklist == null)) {
            marshaller.writeValue(10, transactionBlacklist);
        }
        return marshaller.array();
    }
}