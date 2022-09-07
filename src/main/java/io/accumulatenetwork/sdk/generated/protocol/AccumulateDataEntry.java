package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.accumulatenetwork.sdk.protocol.DataEntry;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.Hex2DDeserializer;
import io.accumulatenetwork.sdk.support.serializers.Hex2DSerializer;
// UnionType: DataEntryType
// UnionValue: Accumulate

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("AccumulateDataEntry")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccumulateDataEntry implements DataEntry {
	public final DataEntryType type = DataEntryType.ACCUMULATE;
	private byte[][] data;

    //
	@JsonDeserialize(using = Hex2DDeserializer.class)
	public byte[][] getData() {
	    return data;
	}
	@JsonSerialize(using = Hex2DSerializer.class)
	public void setData(final byte[][] value) {
	    this.data = value;
	}

	public AccumulateDataEntry data(final byte[][] value) {
	    setData(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.data == null || this.data.length == 0)) {
            marshaller.writeBytes(2, this.data);
        }
        return marshaller.array();
    }
}
