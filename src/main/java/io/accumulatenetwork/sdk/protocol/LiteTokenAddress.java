package io.accumulatenetwork.sdk.protocol;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class LiteTokenAddress extends LiteAddress {

    public LiteTokenAddress(final URI url) throws MalformedURLException {
        super(url);
        if (byteValue.length != 20) {
            byteValue = null;
        }
    }

    public byte[] getByteValue() {
        return byteValue;
    }

    public static LiteTokenAddress parse(final String str) {
        try {
            URI uri = new URI(str);
            return new LiteTokenAddress(uri);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
