package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.DataEntry;
import io.accumulatenetwork.sdk.protocol.TransactionBody;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: TransactionType
// UnionValue: WriteDataTo

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("WriteDataTo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WriteDataTo implements TransactionBody {
	public final TransactionType type = TransactionType.WRITE_DATA_TO;
	private Url recipient;
	private DataEntry entry;

    //
	public Url getRecipient() {
	    return recipient;
	}
	public void setRecipient(final Url value) {
	    this.recipient = value;
	}

	public WriteDataTo recipient(final Url value) {
	    setRecipient(value);
	    return this;
	}
	public WriteDataTo recipient(final String value) {
	    setRecipient(Url.parse(value));
	    return this;
	}
	public DataEntry getEntry() {
	    return entry;
	}
	public void setEntry(final DataEntry value) {
	    this.entry = value;
	}

	public WriteDataTo entry(final DataEntry value) {
	    setEntry(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.recipient == null)) {
            marshaller.writeUrl(2, this.recipient);
        }
        if (!(this.entry == null)) {
            marshaller.writeValue(3, entry);
        }
        return marshaller.array();
    }
}