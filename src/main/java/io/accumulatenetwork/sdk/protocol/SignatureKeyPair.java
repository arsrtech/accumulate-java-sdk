package io.accumulatenetwork.sdk.protocol;

import com.iwebpp.crypto.TweetNaclFast.Signature.KeyPair;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;

public class SignatureKeyPair {

    private KeyPair keyPair;
    private SignatureType signatureType;


    public SignatureKeyPair(final KeyPair keyPair, final SignatureType signatureType) {
        this.keyPair = keyPair;
        this.signatureType = signatureType;
    }

    public byte[] getPublicKey() {
        return keyPair.getPublicKey();
    }


    public byte[] getPrivateKey() {
        return keyPair.getSecretKey();
    }

    public SignatureType getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(final SignatureType signatureType) {
        this.signatureType = signatureType;
    }

    public byte[] getPublicKeyHash() {
        return sha256(getPublicKey());
    }
}
