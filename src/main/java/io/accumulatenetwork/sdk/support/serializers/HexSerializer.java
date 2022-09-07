package io.accumulatenetwork.sdk.support.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;

import java.io.IOException;

public class HexSerializer extends ByteArraySerializer {

    @Override
    public void serialize(final byte[] value, final JsonGenerator g, final SerializerProvider provider) throws IOException {
        g.writeString(Hex.encodeHexString(value));
    }

    @Override
    public void serializeWithType(final byte[] value, final JsonGenerator g, final SerializerProvider provider, final TypeSerializer typeSer)
            throws IOException {
        final WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_EMBEDDED_OBJECT));
        g.writeString(Hex.encodeHexString(value));
        typeSer.writeTypeSuffix(g, typeIdDef);
    }
}
