package io.accumulatenetwork.sdk.generated.apiv2;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.HexDeserializer;
import io.accumulatenetwork.sdk.support.serializers.HexSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("Signer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Signer implements Marhallable {
	private byte[] publicKey;
	private long timestamp;
	private Url url;
	private long version;
	private SignatureType signatureType;
	private boolean useSimpleHash;

    //
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getPublicKey() {
	    return publicKey;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setPublicKey(final byte[] value) {
	    this.publicKey = value;
	}

	public Signer publicKey(final byte[] value) {
	    setPublicKey(value);
	    return this;
	}
	public Signer publicKey(final String value) {
		try {
			setPublicKey(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}
	public long getTimestamp() {
	    return timestamp;
	}
	public void setTimestamp(final long value) {
	    this.timestamp = value;
	}

	public Signer timestamp(final long value) {
	    setTimestamp(value);
	    return this;
	}
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public Signer url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public Signer url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}
	public long getVersion() {
	    return version;
	}
	public void setVersion(final long value) {
	    this.version = value;
	}

	public Signer version(final long value) {
	    setVersion(value);
	    return this;
	}
	public SignatureType getSignatureType() {
	    return signatureType;
	}
	public void setSignatureType(final SignatureType value) {
	    this.signatureType = value;
	}

	public Signer signatureType(final SignatureType value) {
	    setSignatureType(value);
	    return this;
	}
	public boolean getUseSimpleHash() {
	    return useSimpleHash;
	}
	public void setUseSimpleHash(final boolean value) {
	    this.useSimpleHash = value;
	}

	public Signer useSimpleHash(final boolean value) {
	    setUseSimpleHash(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.publicKey == null || this.publicKey.length == 0)) {
            marshaller.writeBytes(1, this.publicKey);
        }
        if (!(this.timestamp == 0)) {
            marshaller.writeUint(2, this.timestamp);
        }
        if (!(this.url == null)) {
            marshaller.writeUrl(3, this.url);
        }
        if (!(this.version == 0)) {
            marshaller.writeUint(4, this.version);
        }
        if (!(this.signatureType == null)) {
            marshaller.writeValue(5, signatureType);
        }
        if (!(!this.useSimpleHash)) {
            marshaller.writeBool(6, this.useSimpleHash);
        }
        return marshaller.array();
    }
}