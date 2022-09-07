package io.accumulatenetwork.sdk.protocol;


import io.accumulatenetwork.sdk.commons.codec.DecoderException;
import io.accumulatenetwork.sdk.commons.codec.binary.Hex;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;


public class LiteAddress extends Url {

    protected byte[] byteValue;

    public LiteAddress(final URI url) throws MalformedURLException {
        super(url);
        try {
            final byte[] host = Hex.decodeHex(hostName());
            final int i = host.length - 4;
            final byte[] byteValue = Arrays.copyOfRange(host, 0, i);
            final byte[] byteCheck = Arrays.copyOfRange(host, i, host.length - i);
            final String authority = authority();
            final String hexValue = authority.substring(0, authority.length() - 8);
            final byte[] checkSum = sha256(hexValue.getBytes(StandardCharsets.UTF_8));
            if (Arrays.compare(byteCheck, Arrays.copyOfRange(checkSum, 28, checkSum.length - 28)) != 0) {
                throw new RuntimeException("Invalid checksum");
            }
            this.byteValue = byteValue;
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }


    public byte[] getByteValue() {
        return byteValue;
    }

    public boolean isValid() {
        return byteValue != null;
    }

    public static LiteAddress parse(final String str) {
        try {
            URI uri = new URI(str);
            return new LiteAddress(uri);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
