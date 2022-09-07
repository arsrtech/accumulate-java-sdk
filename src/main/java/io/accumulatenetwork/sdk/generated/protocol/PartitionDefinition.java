package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.Hex2DDeserializer;
import io.accumulatenetwork.sdk.support.serializers.Hex2DSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("PartitionDefinition")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartitionDefinition implements Marhallable {
	private String partitionID;
	private byte[][] validatorKeys;

    //
	public String getPartitionID() {
	    return partitionID;
	}
	public void setPartitionID(final String value) {
	    this.partitionID = value;
	}

	public PartitionDefinition partitionID(final String value) {
	    setPartitionID(value);
	    return this;
	}
	@JsonDeserialize(using = Hex2DDeserializer.class)
	public byte[][] getValidatorKeys() {
	    return validatorKeys;
	}
	@JsonSerialize(using = Hex2DSerializer.class)
	public void setValidatorKeys(final byte[][] value) {
	    this.validatorKeys = value;
	}

	public PartitionDefinition validatorKeys(final byte[][] value) {
	    setValidatorKeys(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.partitionID == null || this.partitionID.length() == 0)) {
            marshaller.writeString(1, this.partitionID);
        }
        if (!(this.validatorKeys == null || this.validatorKeys.length == 0)) {
            marshaller.writeBytes(2, this.validatorKeys);
        }
        return marshaller.array();
    }
}
