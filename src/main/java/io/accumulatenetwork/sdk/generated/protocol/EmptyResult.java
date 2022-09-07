package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.TransactionResult;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: TransactionType
// UnionValue: Unknown

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("EmptyResult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmptyResult implements TransactionResult {
	public final TransactionType type = TransactionType.UNKNOWN;

    //

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        return marshaller.array();
    }
}