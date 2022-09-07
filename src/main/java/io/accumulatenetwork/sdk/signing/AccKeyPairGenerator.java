package io.accumulatenetwork.sdk.signing;

import com.iwebpp.crypto.TweetNaclFast;
import com.iwebpp.crypto.TweetNaclFast.Signature.KeyPair;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;


public class AccKeyPairGenerator {

    public static KeyPair generate(final SignatureType signatureType) {
        switch (signatureType) {
            case ED25519:
            case RCD1:
                return TweetNaclFast.Signature.keyPair();
        }
        throw new IllegalArgumentException(String.format("Signature type %s is not supported (yet.)", signatureType.name()));
    }
}
