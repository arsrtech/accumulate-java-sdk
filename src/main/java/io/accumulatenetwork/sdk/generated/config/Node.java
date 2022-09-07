package io.accumulatenetwork.sdk.generated.config;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("Node")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node implements Marhallable {
	private String address;
	private NodeType type;

    //
	public String getAddress() {
	    return address;
	}
	public void setAddress(final String value) {
	    this.address = value;
	}

	public Node address(final String value) {
	    setAddress(value);
	    return this;
	}
	public NodeType getType() {
	    return type;
	}
	public void setType(final NodeType value) {
	    this.type = value;
	}

	public Node type(final NodeType value) {
	    setType(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.address == null || this.address.length() == 0)) {
            marshaller.writeString(1, this.address);
        }
        if (!(this.type == null)) {
            marshaller.writeValue(2, type);
        }
        return marshaller.array();
    }
}
