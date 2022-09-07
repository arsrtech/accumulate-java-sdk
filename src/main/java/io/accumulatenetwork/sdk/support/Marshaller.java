package io.accumulatenetwork.sdk.support;

import io.accumulatenetwork.sdk.protocol.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.function.Consumer;

public class Marshaller {

    private static final Class<? extends byte[]> byteArrayClass = new byte[0].getClass();
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    final DataOutputStream stream = new DataOutputStream(byteArrayOutputStream);


    public void writeString(final int fieldNr, final String value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                stream.write(toUIntBuffer(BigInteger.valueOf(value.length())));
                stream.write(value.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeString(final int fieldNr, final String[] values) {
        if (values != null) {
            for (final String value : values) {
                writeString(fieldNr, value);
            }
        }
    }


    public void writeUrl(final int fieldNr, final Url... values) {
        if (values != null) {
            for (final var value : values) {
                writeString(fieldNr, value.string());
            }
        }
    }


    public void writeInt(final int fieldNr, final Integer value) {
        if (value != null) {
            try {
                stream.writeByte(+fieldNr);
                stream.writeInt(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeLong(final int fieldNr, final Long value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                stream.writeLong(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeBool(final int fieldNr, final Boolean value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                stream.writeBoolean(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeUint(final int fieldNr, final Integer value) {
        if (value != null) {
            writeUint(fieldNr, BigInteger.valueOf(value));
        }
    }

    public void writeUint(final int fieldNr, final Long value) {
        if (value != null) {
            writeUint(fieldNr, BigInteger.valueOf(value));
        }
    }

    public void writeUint(final int fieldNr, final BigDecimal value) {
        if (value != null) {
            writeUint(fieldNr, value.toBigInteger());
        }
    }

    public void writeUint(final int fieldNr, final BigInteger value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                internalWriteUInt(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void internalWriteUInt(BigInteger value) throws IOException {
        BigInteger number = value;
        while (number.compareTo(BigInteger.valueOf(0x80)) >= 0) {
            BigInteger clb = clearBit(number, 8);
            BigInteger clearBit = clb.or(BigInteger.valueOf(0x80));
            stream.write(toByteArray(clearBit.toByteArray()));
            number = number.shiftRight(7);
        }
        stream.write(toByteArray(clearBit(number, 8).toByteArray()));
    }

    public void writeEnum(final int fieldNr, final IntValueEnum value) {
        if (value != null) {
            writeUint(fieldNr, value.getValue());
        }
    }

    public void writeHash(final int fieldNr, final byte[] value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                stream.write(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeBytes(final int fieldNr, final byte[] value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                internalWriteUInt(BigInteger.valueOf(value.length));
                stream.write(value);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeBytes(final int fieldNr, final byte[][] data) {
        for (byte[] item : data) {
            writeBytes(fieldNr, item);
        }
    }

    public void writeBigInt(final int fieldNr, final BigInteger value) {
        if (value != null) {
            writeBytes(fieldNr, value.toByteArray());
        }
    }

    public void writeTime(final int fieldNr, final OffsetDateTime value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                stream.writeLong(value.atZoneSameInstant(ZoneOffset.UTC).toInstant().toEpochMilli());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeTxid(final int fieldNr, final TxID value) {
        if (value == null) {
            writeString(fieldNr, "");
        } else {
            writeString(fieldNr, value.getUrl().string());
        }
    }

    public void writeTxid(final int fieldNr, final TxID[] entries) {
        for (var entry : entries) {
            writeTxid(fieldNr, entry);
        }
    }

    public void writeRawJson(final int fieldNr, final RawJson value) {
        throw new NotImplementedException(); // TODO
    }

    public void writeDuration(final int fieldNr, final Duration value) {
        if (value != null) {
            try {
                stream.writeByte(fieldNr);
                internalWriteUInt(BigInteger.valueOf(value.getSeconds()));
                internalWriteUInt(BigInteger.valueOf(value.getNano()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeAny(final int fieldNr, final Object value) {
        if (value != null) {
            writeValue(fieldNr, value);
        }
    }

    public void writeValue(final int fieldNr, final Object value) {
        when(value,
                is(byteArrayClass, v -> writeBytes(fieldNr, v)),
                is(IntValueEnum.class, v -> writeEnum(fieldNr, v)),
                is(Marhallable.class, v -> writeBytes(fieldNr, v.marshalBinary())),
                is(String.class, v -> writeString(fieldNr, v)),
                is(Integer.class, v -> writeInt(fieldNr, v)),
                is(Long.class, v -> writeLong(fieldNr, v)),
                is(Boolean.class, v -> writeBool(fieldNr, v)),
                is(BigInteger.class, v -> writeUint(fieldNr, v)),
                is(BigDecimal.class, v -> writeUint(fieldNr, v)),
                is(OffsetDateTime.class, v -> writeTime(fieldNr, v))
        );
    }

    public byte[] array() {
        if (byteArrayOutputStream.size() == 0) {
            return new byte[]{(byte) 0x80};
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toUIntBuffer(BigInteger value) {
        byte[] bytesArray = new byte[0];
        while (value.compareTo(BigInteger.valueOf(0x80)) >= 0) {
            BigInteger clb = clearBit(value, 8);
            BigInteger clearBit = clb.or(BigInteger.valueOf(0x80));
            bytesArray = append(bytesArray, toByteArray(clearBit.toByteArray()));
            value = value.shiftRight(7);
        }
        bytesArray = append(bytesArray, toByteArray(clearBit(value, 8).toByteArray()));
        return bytesArray;
    }

    public static byte[] toUIntBuffer(final long value) {
        return toUIntBuffer(BigInteger.valueOf(value));
    }

    public static byte[] append(final byte[]... arrays) {
        byte[] copy = new byte[0];
        if (arrays != null) {
            for (final byte[] array : arrays) {
                if (array != null) {
                    copy = ArrayUtils.addAll(copy, array);
                }
            }
        }
        return copy;
    }


    private static BigInteger clearBit(final BigInteger n, final int len) {
        BigInteger tes = n;
        for (int i = len; i < n.bitLength(); i++) {
            tes = tes.clearBit(i);
        }
        return tes;
    }

    private static byte[] toByteArray(final byte[] value) {
        byte[] bytes = new byte[1];
        if (value.length > 1) {
            bytes[0] = value[1];
        } else {
            bytes[0] = value[0];
        }
        return bytes;
    }


    public static <T> void when(final Object o, final Consumer... a) {
        for (Consumer consumer : a) {
            consumer.accept(o);
        }
    }

    public static <T> Consumer is(final Class<T> cls, final Consumer<T> c) {
        return obj -> Optional.of(obj).filter(cls::isInstance).map(cls::cast).ifPresent(c);
    }
}
