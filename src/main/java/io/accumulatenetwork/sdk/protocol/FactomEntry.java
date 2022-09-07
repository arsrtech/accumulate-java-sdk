package io.accumulatenetwork.sdk.protocol;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FactomEntry {

    private final byte[] data;

    private List<FactomExtRef> extRefs = new ArrayList<>();


    public FactomEntry() {
        this.data = null;
    }

    public FactomEntry(final byte[] data) {
        this.data = data;
    }

    public FactomEntry(final String data) {
        this.data = data.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getData() {
        return data;
    }

    public List<FactomExtRef> getExtRefs() {
        return extRefs;
    }

    public FactomEntry addExtRef(final byte[] data) {
        getExtRefs().add(new FactomExtRef(data));
        return this;
    }

    public FactomEntry addExtRef(final String data) {
        getExtRefs().add(new FactomExtRef(data));
        return this;
    }

    public FactomEntry addExtRef(final FactomExtRef extRef) {
        getExtRefs().add(extRef);
        return this;
    }
}
