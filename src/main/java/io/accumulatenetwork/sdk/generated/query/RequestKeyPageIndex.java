package io.accumulatenetwork.sdk.generated.query;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.HexDeserializer;
import io.accumulatenetwork.sdk.support.serializers.HexSerializer;
// UnionType: QueryType
// UnionValue: KeyPageIndex

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("RequestKeyPageIndex")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestKeyPageIndex implements Marhallable {
	public final QueryType type = QueryType.KEY_PAGE_INDEX;
	private Url url;
	private byte[] key;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public RequestKeyPageIndex url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public RequestKeyPageIndex url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getKey() {
	    return key;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setKey(final byte[] value) {
	    this.key = value;
	}

	public RequestKeyPageIndex key(final byte[] value) {
	    setKey(value);
	    return this;
	}
	public RequestKeyPageIndex key(final String value) {
		try {
			setKey(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        marshaller.writeValue(1, type);
        if (!(this.url == null)) {
            marshaller.writeUrl(2, this.url);
        }
        if (!(this.key == null || this.key.length == 0)) {
            marshaller.writeBytes(3, this.key);
        }
        return marshaller.array();
    }
}