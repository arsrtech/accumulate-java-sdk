package io.accumulatenetwork.sdk.support.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigInteger;

public class GoBigIntSerializer extends StdSerializer<BigInteger> {

    public GoBigIntSerializer() {
        super(BigInteger.class);
    }

    public GoBigIntSerializer(Class<BigInteger> t) {
        super(t);
    }

    @Override
    public void serialize(final BigInteger value, final JsonGenerator g, final SerializerProvider provider) throws IOException {
        g.writeString(value.toString());
    }

    @Override
    public void serializeWithType(final BigInteger value, final JsonGenerator g, final SerializerProvider provider, final TypeSerializer typeSer)
            throws IOException {
        final WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.VALUE_STRING));
        g.writeString(value.toString());
        typeSer.writeTypeSuffix(g, typeIdDef);
    }
}
