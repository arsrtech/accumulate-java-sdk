package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.commons.codec.DecoderException;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

public class TxID {

    private Url url;
    private byte[] hash;

    public TxID() {
    }

    public TxID(final Url url) {
        this.url = url;
    }

    @JsonValue
    public Url getUrl() {
        return url;
    }

    public void setUrl(final Url url) {
        this.url = url;
    }

    private void extractHash(final Url url) {
        final String hashHex = StringUtils.substringBetween(url.string(), "//", "@");
        try {
            setHash(Hex.decodeHex(hashHex));
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getHash() {
        if (hash == null) {
            extractHash(getUrl());
        }
        return hash;
    }

    public void setHash(final byte[] hash) {
        this.hash = hash;
    }


    @JsonCreator
    public static TxID fromJsonNode(final JsonNode jsonNode) {
        if (jsonNode.isTextual()) {
            return new TxID(Url.parse(jsonNode.asText()));
        }
        throw new RuntimeException(String.format("Can't parse TxID from '%s'", jsonNode.toPrettyString()));
    }

    @Override
    public String toString() {
        return url.string();
    }
}
