package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.KeyPageOperation;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: KeyPageOperationType
// UnionValue: Update

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("UpdateKeyOperation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateKeyOperation implements KeyPageOperation {
	public final KeyPageOperationType type = KeyPageOperationType.UPDATE;
	private KeySpecParams oldEntry;
	private KeySpecParams newEntry;

    //
	public KeySpecParams getOldEntry() {
	    return oldEntry;
	}
	public void setOldEntry(final KeySpecParams value) {
	    this.oldEntry = value;
	}

	public UpdateKeyOperation oldEntry(final KeySpecParams value) {
	    setOldEntry(value);
	    return this;
	}
	public KeySpecParams getNewEntry() {
	    return newEntry;
	}
	public void setNewEntry(final KeySpecParams value) {
	    this.newEntry = value;
	}

	public UpdateKeyOperation newEntry(final KeySpecParams value) {
	    setNewEntry(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.oldEntry == null)) {
            marshaller.writeValue(2, oldEntry);
        }
        if (!(this.newEntry == null)) {
            marshaller.writeValue(3, newEntry);
        }
        return marshaller.array();
    }
}
