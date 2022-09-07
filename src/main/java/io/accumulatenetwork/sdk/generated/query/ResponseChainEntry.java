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
import io.accumulatenetwork.sdk.support.serializers.HexDeserializer;
import io.accumulatenetwork.sdk.support.serializers.HexSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ResponseChainEntry")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseChainEntry implements Marhallable {
	private ChainType type;
	private long height;
	private byte[] entry;
	private byte[][] state;
	private GeneralReceipt receipt;

    //
	public ChainType getType() {
	    return type;
	}
	public void setType(final ChainType value) {
	    this.type = value;
	}

	public ResponseChainEntry type(final ChainType value) {
	    setType(value);
	    return this;
	}
	public long getHeight() {
	    return height;
	}
	public void setHeight(final long value) {
	    this.height = value;
	}

	public ResponseChainEntry height(final long value) {
	    setHeight(value);
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getEntry() {
	    return entry;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setEntry(final byte[] value) {
	    this.entry = value;
	}

	public ResponseChainEntry entry(final byte[] value) {
	    setEntry(value);
	    return this;
	}
	public ResponseChainEntry entry(final String value) {
		try {
			setEntry(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}
	@JsonDeserialize(using = Hex2DDeserializer.class)
	public byte[][] getState() {
	    return state;
	}
	@JsonSerialize(using = Hex2DSerializer.class)
	public void setState(final byte[][] value) {
	    this.state = value;
	}

	public ResponseChainEntry state(final byte[][] value) {
	    setState(value);
	    return this;
	}
	public GeneralReceipt getReceipt() {
	    return receipt;
	}
	public void setReceipt(final GeneralReceipt value) {
	    this.receipt = value;
	}

	public ResponseChainEntry receipt(final GeneralReceipt value) {
	    setReceipt(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.type == null)) {
            marshaller.writeValue(1, type);
        }
        marshaller.writeUint(2, this.height);
        if (!(this.entry == null || this.entry.length == 0)) {
            marshaller.writeBytes(3, this.entry);
        }
        if (!(this.state == null || this.state.length == 0)) {
            marshaller.writeBytes(4, this.state);
        }
        if (!(this.receipt == null)) {
            marshaller.writeValue(5, receipt);
        }
        return marshaller.array();
    }
}
