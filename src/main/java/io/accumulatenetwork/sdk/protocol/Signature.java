package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.accumulatenetwork.sdk.generated.protocol.ED25519Signature;
import io.accumulatenetwork.sdk.generated.protocol.PartitionSignature;
import io.accumulatenetwork.sdk.generated.protocol.RCD1Signature;
import io.accumulatenetwork.sdk.generated.protocol.ReceiptSignature;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ED25519Signature.class, name = "ed25519"),
        @JsonSubTypes.Type(value = RCD1Signature.class, name = "rcd1"),
        @JsonSubTypes.Type(value = ReceiptSignature.class, name = "receipt"),
        @JsonSubTypes.Type(value = PartitionSignature.class, name = "partition"),
})
public interface Signature {
}
