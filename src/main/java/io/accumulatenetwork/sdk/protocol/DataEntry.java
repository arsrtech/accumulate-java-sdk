package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.accumulatenetwork.sdk.generated.protocol.AccumulateDataEntry;
import io.accumulatenetwork.sdk.generated.protocol.FactomDataEntry;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AccumulateDataEntry.class, name = "accumulate"),
        @JsonSubTypes.Type(value = FactomDataEntry.class, name = "factom"),
})
public interface DataEntry extends Marhallable {
}
