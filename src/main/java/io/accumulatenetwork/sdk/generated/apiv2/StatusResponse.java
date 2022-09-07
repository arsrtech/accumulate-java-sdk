package io.accumulatenetwork.sdk.generated.apiv2;

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
import io.accumulatenetwork.sdk.support.serializers.HexDeserializer;
import io.accumulatenetwork.sdk.support.serializers.HexSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("StatusResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponse implements Marhallable {
	private boolean ok;
	private int bvnHeight;
	private int dnHeight;
	private java.time.OffsetDateTime bvnTime;
	private java.time.OffsetDateTime dnTime;
	private long lastDirectoryAnchorHeight;
	private byte[] bvnRootHash;
	private byte[] dnRootHash;
	private byte[] bvnBptHash;
	private byte[] dnBptHash;

    //
	public boolean getOk() {
	    return ok;
	}
	public void setOk(final boolean value) {
	    this.ok = value;
	}

	public StatusResponse ok(final boolean value) {
	    setOk(value);
	    return this;
	}
	public int getBvnHeight() {
	    return bvnHeight;
	}
	public void setBvnHeight(final int value) {
	    this.bvnHeight = value;
	}

	public StatusResponse bvnHeight(final int value) {
	    setBvnHeight(value);
	    return this;
	}
	public int getDnHeight() {
	    return dnHeight;
	}
	public void setDnHeight(final int value) {
	    this.dnHeight = value;
	}

	public StatusResponse dnHeight(final int value) {
	    setDnHeight(value);
	    return this;
	}
	public java.time.OffsetDateTime getBvnTime() {
	    return bvnTime;
	}
	public void setBvnTime(final java.time.OffsetDateTime value) {
	    this.bvnTime = value;
	}

	public StatusResponse bvnTime(final java.time.OffsetDateTime value) {
	    setBvnTime(value);
	    return this;
	}
	public java.time.OffsetDateTime getDnTime() {
	    return dnTime;
	}
	public void setDnTime(final java.time.OffsetDateTime value) {
	    this.dnTime = value;
	}

	public StatusResponse dnTime(final java.time.OffsetDateTime value) {
	    setDnTime(value);
	    return this;
	}
	public long getLastDirectoryAnchorHeight() {
	    return lastDirectoryAnchorHeight;
	}
	public void setLastDirectoryAnchorHeight(final long value) {
	    this.lastDirectoryAnchorHeight = value;
	}

	public StatusResponse lastDirectoryAnchorHeight(final long value) {
	    setLastDirectoryAnchorHeight(value);
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getBvnRootHash() {
	    return bvnRootHash;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setBvnRootHash(final byte[] value) {
	    this.bvnRootHash = value;
	}

	public StatusResponse bvnRootHash(final byte[] value) {
	    setBvnRootHash(value);
	    return this;
	}
	public StatusResponse bvnRootHash(final String value) {
		try {
			setBvnRootHash(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getDnRootHash() {
	    return dnRootHash;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setDnRootHash(final byte[] value) {
	    this.dnRootHash = value;
	}

	public StatusResponse dnRootHash(final byte[] value) {
	    setDnRootHash(value);
	    return this;
	}
	public StatusResponse dnRootHash(final String value) {
		try {
			setDnRootHash(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getBvnBptHash() {
	    return bvnBptHash;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setBvnBptHash(final byte[] value) {
	    this.bvnBptHash = value;
	}

	public StatusResponse bvnBptHash(final byte[] value) {
	    setBvnBptHash(value);
	    return this;
	}
	public StatusResponse bvnBptHash(final String value) {
		try {
			setBvnBptHash(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getDnBptHash() {
	    return dnBptHash;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setDnBptHash(final byte[] value) {
	    this.dnBptHash = value;
	}

	public StatusResponse dnBptHash(final byte[] value) {
	    setDnBptHash(value);
	    return this;
	}
	public StatusResponse dnBptHash(final String value) {
		try {
			setDnBptHash(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(!this.ok)) {
            marshaller.writeBool(1, this.ok);
        }
        if (!(this.bvnHeight == 0)) {
            marshaller.writeInt(2, this.bvnHeight);
        }
        if (!(this.dnHeight == 0)) {
            marshaller.writeInt(3, this.dnHeight);
        }
        if (!(this.bvnTime == null)) {
            marshaller.writeTime(4, this.bvnTime);
        }
        if (!(this.dnTime == null)) {
            marshaller.writeTime(5, this.dnTime);
        }
        if (!(this.lastDirectoryAnchorHeight == 0)) {
            marshaller.writeUint(6, this.lastDirectoryAnchorHeight);
        }
        if (!(this.bvnRootHash == null || this.bvnRootHash.length == 0)) {
            marshaller.writeHash(7, this.bvnRootHash);
        }
        if (!(this.dnRootHash == null || this.dnRootHash.length == 0)) {
            marshaller.writeHash(8, this.dnRootHash);
        }
        if (!(this.bvnBptHash == null || this.bvnBptHash.length == 0)) {
            marshaller.writeHash(9, this.bvnBptHash);
        }
        if (!(this.dnBptHash == null || this.dnBptHash.length == 0)) {
            marshaller.writeHash(10, this.dnBptHash);
        }
        return marshaller.array();
    }
}