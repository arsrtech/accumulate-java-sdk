package io.accumulatenetwork.sdk.generated.query;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.accumulatenetwork.sdk.generated.managed.ChainType;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.Hex2DDeserializer;
import io.accumulatenetwork.sdk.support.serializers.Hex2DSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ChainState")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChainState implements Marhallable {
	private String name;
	private ChainType type;
	private long height;
	private byte[][] roots;

    //
	public String getName() {
	    return name;
	}
	public void setName(final String value) {
	    this.name = value;
	}

	public ChainState name(final String value) {
	    setName(value);
	    return this;
	}
	public ChainType getType() {
	    return type;
	}
	public void setType(final ChainType value) {
	    this.type = value;
	}

	public ChainState type(final ChainType value) {
	    setType(value);
	    return this;
	}
	public long getHeight() {
	    return height;
	}
	public void setHeight(final long value) {
	    this.height = value;
	}

	public ChainState height(final long value) {
	    setHeight(value);
	    return this;
	}
	@JsonDeserialize(using = Hex2DDeserializer.class)
	public byte[][] getRoots() {
	    return roots;
	}
	@JsonSerialize(using = Hex2DSerializer.class)
	public void setRoots(final byte[][] value) {
	    this.roots = value;
	}

	public ChainState roots(final byte[][] value) {
	    setRoots(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.name == null || this.name.length() == 0)) {
            marshaller.writeString(1, this.name);
        }
        if (!(this.type == null)) {
            marshaller.writeValue(2, type);
        }
        if (!(this.height == 0)) {
            marshaller.writeUint(3, this.height);
        }
        if (!(this.roots == null || this.roots.length == 0)) {
            marshaller.writeBytes(4, this.roots);
        }
        return marshaller.array();
    }
}