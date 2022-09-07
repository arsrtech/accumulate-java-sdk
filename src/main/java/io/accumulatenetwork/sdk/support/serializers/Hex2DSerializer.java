package io.accumulatenetwork.sdk.support.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;

import java.io.IOException;

public class Hex2DSerializer extends StdSerializer<byte[][]> {

    public Hex2DSerializer() {
        super(byte[][].class);
    }

    @Override
    public void serialize(final byte[][] values, final JsonGenerator g, final SerializerProvider provider) throws IOException {
        final String[] hexValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            hexValues[i] = Hex.encodeHexString(values[i]);
        }
        g.writeArray(hexValues, 0, hexValues.length);
    }

    @Override
    public void serializeWithType(final byte[][] values, final JsonGenerator g, final SerializerProvider provider, final TypeSerializer typeSer)
            throws IOException {
        final String[] hexValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            hexValues[i] = Hex.encodeHexString(values[i]);
        }
        final WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(hexValues, JsonToken.VALUE_EMBEDDED_OBJECT));
        g.writeArray(hexValues, 0, hexValues.length);
        typeSer.writeTypeSuffix(g, typeIdDef);
    }
}
