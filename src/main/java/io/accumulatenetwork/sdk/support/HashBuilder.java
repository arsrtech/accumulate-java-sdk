package io.accumulatenetwork.sdk.support;

import io.accumulatenetwork.sdk.protocol.Url;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;

public class HashBuilder {

    private final List<byte[]> hashList = new ArrayList<>();

    public HashBuilder addHash(final byte[] value) {
        if (value != null && value.length > 0) {
            hashList.add(value);
        }
        return this;
    }

    public HashBuilder addBytes(final byte[] value) {
        if (value != null && value.length > 0) {
            add(value);
        }
        return this;
    }

    public HashBuilder addUrl(final Url value) {
        if (value != null) {
            add(value.string().getBytes(StandardCharsets.UTF_8));
        }
        return this;
    }

    public HashBuilder addUInt(final BigInteger value) {
        if (value != null) {
            add(Marshaller.toUIntBuffer(value));
        }
        return this;
    }

    private void add(final byte[] value) {
        if (value != null && value.length > 0) {
            hashList.add(sha256(value));
        }
    }

    public byte[] merkleHash() {
        final MerkleRootBuilder merkleRootBuilder = new MerkleRootBuilder();
        hashList.forEach(hash -> {
            merkleRootBuilder.addToMerkleTree(hash);
        });
        return merkleRootBuilder.getMDRoot();
    }

    public byte[] getCheckSum() {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        hashList.forEach(bytes -> {
            try {
                bos.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return sha256(bos.toByteArray()); // TODO check if this is correct
    }
}
