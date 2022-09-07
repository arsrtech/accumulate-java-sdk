package io.accumulatenetwork.sdk.support.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigInteger;

public class GoBigIntDeserializer extends StdDeserializer<BigInteger> {

    public GoBigIntDeserializer() {
        super(byte[].class);
    }

    @Override
    public BigInteger deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.isTextual()) {
            return new BigInteger(node.asText());
        }
        throw new IllegalArgumentException("JSON node should be a string. " + node.toPrettyString());
    }
}
