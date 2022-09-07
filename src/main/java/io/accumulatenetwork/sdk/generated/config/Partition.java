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
@JsonTypeName("Partition")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Partition implements Marhallable {
	private String id;
	private NetworkType type;
	private int basePort;
	private Node[] nodes;

    //
	public String getId() {
	    return id;
	}
	public void setId(final String value) {
	    this.id = value;
	}

	public Partition id(final String value) {
	    setId(value);
	    return this;
	}
	public NetworkType getType() {
	    return type;
	}
	public void setType(final NetworkType value) {
	    this.type = value;
	}

	public Partition type(final NetworkType value) {
	    setType(value);
	    return this;
	}
	public int getBasePort() {
	    return basePort;
	}
	public void setBasePort(final int value) {
	    this.basePort = value;
	}

	public Partition basePort(final int value) {
	    setBasePort(value);
	    return this;
	}
	public Node[] getNodes() {
	    return nodes;
	}
	public void setNodes(final Node[] value) {
	    this.nodes = value;
	}

	public Partition nodes(final Node[] value) {
	    setNodes(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.id == null || this.id.length() == 0)) {
            marshaller.writeString(1, this.id);
        }
        if (!(this.type == null)) {
            marshaller.writeValue(2, type);
        }
        if (!(this.basePort == 0)) {
            marshaller.writeInt(3, this.basePort);
        }
        if (!(this.nodes == null)) {
            marshaller.writeValue(4, nodes);
        }
        return marshaller.array();
    }
}
