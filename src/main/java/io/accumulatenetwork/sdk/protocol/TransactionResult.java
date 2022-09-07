package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.accumulatenetwork.sdk.generated.protocol.AddCreditsResult;
import io.accumulatenetwork.sdk.generated.protocol.EmptyResult;
import io.accumulatenetwork.sdk.generated.protocol.WriteDataResult;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddCreditsResult.class, name = "addCredits"),
        @JsonSubTypes.Type(value = WriteDataResult.class, name = "writeData"),
        @JsonSubTypes.Type(value = EmptyResult.class, name = "unknown")})
public interface TransactionResult {
}
