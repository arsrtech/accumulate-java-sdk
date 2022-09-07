package io.accumulatenetwork.sdk.support.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.accumulatenetwork.sdk.commons.codec.DecoderException;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;

import java.io.IOException;

public class HexDeserializer extends StdDeserializer<byte[]> {

    public HexDeserializer() {
        super(byte[].class);
    }

    @Override
    public byte[] deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.isTextual()) {
            try {
                return Hex.decodeHex(node.asText());
            } catch (DecoderException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("JSON node should be a hex string. " + node.toPrettyString());
    }
}
