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
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.Marshaller;
import io.accumulatenetwork.sdk.support.serializers.GoBigIntDeserializer;
import io.accumulatenetwork.sdk.support.serializers.GoBigIntSerializer;
import io.accumulatenetwork.sdk.support.serializers.HexDeserializer;
import io.accumulatenetwork.sdk.support.serializers.HexSerializer;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("TokenDeposit")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDeposit implements Marhallable {
	private Url url;
	private java.math.BigInteger amount;
	private byte[] txid;

    //
	public Url getUrl() {
	    return url;
	}
	public void setUrl(final Url value) {
	    this.url = value;
	}

	public TokenDeposit url(final Url value) {
	    setUrl(value);
	    return this;
	}
	public TokenDeposit url(final String value) {
	    setUrl(Url.parse(value));
	    return this;
	}
	@JsonDeserialize(using = GoBigIntDeserializer.class)
	public java.math.BigInteger getAmount() {
	    return amount;
	}
	@JsonSerialize(using = GoBigIntSerializer.class)
	public void setAmount(final java.math.BigInteger value) {
	    this.amount = value;
	}

	public TokenDeposit amount(final java.math.BigInteger value) {
	    setAmount(value);
	    return this;
	}
	@JsonDeserialize(using = HexDeserializer.class)
	public byte[] getTxid() {
	    return txid;
	}
	@JsonSerialize(using = HexSerializer.class)
	public void setTxid(final byte[] value) {
	    this.txid = value;
	}

	public TokenDeposit txid(final byte[] value) {
	    setTxid(value);
	    return this;
	}
	public TokenDeposit txid(final String value) {
		try {
			setTxid(io.accumulatenetwork.sdk.commons.codec.binary.Hex.decodeHex(value));
		} catch (io.accumulatenetwork.sdk.commons.codec.DecoderException e) {
			throw new RuntimeException(e);
		}
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.url == null)) {
            marshaller.writeUrl(1, this.url);
        }
        if (!((this.amount).equals(java.math.BigInteger.ZERO))) {
            marshaller.writeBigInt(2, this.amount);
        }
        if (!(this.txid == null || this.txid.length == 0)) {
            marshaller.writeBytes(3, this.txid);
        }
        return marshaller.array();
    }
}