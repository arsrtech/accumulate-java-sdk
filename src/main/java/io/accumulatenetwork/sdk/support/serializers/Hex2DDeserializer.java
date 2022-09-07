package io.accumulatenetwork.sdk.support.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.accumulatenetwork.sdk.commons.codec.DecoderException;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;

import java.io.IOException;
import java.util.ArrayList;

public class Hex2DDeserializer extends StdDeserializer<byte[][]> {

    public Hex2DDeserializer() {
        super(byte[][].class);
    }

    @Override
    public byte[][] deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final ArrayList<byte[]> resultList = new ArrayList<>();
        final JsonNode arrNode = jsonParser.getCodec().readTree(jsonParser);
        if (arrNode.isArray()) {
            int maxLen = 0;
            for (final JsonNode node : arrNode) {
                if (node.isTextual()) {
                    try {
                        final byte[] bytes = Hex.decodeHex(node.asText());
                        resultList.add(bytes);
                        if(bytes.length > maxLen) {
                            maxLen = bytes.length;
                        }
                    } catch (DecoderException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new IllegalArgumentException("JSON node should be a hex string. " + node.toPrettyString());
                }
            }
            return resultList.toArray(new byte[resultList.size()][maxLen]);
        }
        throw new IllegalArgumentException("JSON node should be an array of hex strings. " + arrNode.toPrettyString());
    }
}
