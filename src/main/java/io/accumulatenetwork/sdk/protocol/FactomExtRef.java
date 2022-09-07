package io.accumulatenetwork.sdk.protocol;

import java.nio.charset.StandardCharsets;

public class FactomExtRef {

    private byte[] data;

    public FactomExtRef(final byte[] data) {
        this.data = data;
    }

    public FactomExtRef(final String data) {
        this.data = data.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getData() {
        return data;
    }
}
