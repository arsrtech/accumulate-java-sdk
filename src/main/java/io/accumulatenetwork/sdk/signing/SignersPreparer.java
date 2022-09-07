package io.accumulatenetwork.sdk.signing;

import io.accumulatenetwork.sdk.protocol.SignatureKeyPair;
import io.accumulatenetwork.sdk.protocol.Url;

import java.util.ArrayList;
import java.util.List;

public class SignersPreparer {

    private final Url signerUrl;

    private SignatureKeyPair signatureKeyPair;
    private final List<SignatureKeyPair> additionalSignerSignatureKeyPairs = new ArrayList<>();
    private final List<Url> delegators = new ArrayList<>();


    public SignersPreparer(final SignatureKeyPair signatureKeyPair, final Url signerUrl) {
        this.signerUrl = signerUrl;
        this.signatureKeyPair = signatureKeyPair;
    }


    public SignersPreparer withDelegators(final List<Url> delegators) {
        delegators.addAll(delegators);
        return this;
    }

    public SignersPreparer withAdditionalSignerKeys(final List<String> additionalSignerKeys) {
        additionalSignerKeys.addAll(additionalSignerKeys);
        return this;
    }


    public List<Signer> prepareSigners() {
        final List<Signer> signers = new ArrayList<>();
        final var firstSigner = new Signer()
                .withNonceFromTimeNow();
        firstSigner.withDelegators(delegators);
        firstSigner.withType(getSignatureKeyPair().getSignatureType())
                .withUrl(signerUrl)
                .withVersion(1)
                .withSignerPrivateKey(getSignatureKeyPair().getPrivateKey());
        signers.add(firstSigner);

        additionalSignerSignatureKeyPairs.forEach(signatureKeyPair -> {
            final var signer = new Signer()
                    .withDelegators(delegators)
                    .withType(signatureKeyPair.getSignatureType())
                    .withUrl(signerUrl) // FIXME don't they have their own url?
                    .withVersion(1)
                    .withSignerPrivateKey(signatureKeyPair.getPrivateKey());
            signers.add(signer);
        });
        return signers;
    }


    public Url getSignerUrl() {
        return signerUrl;
    }

    public SignatureKeyPair getSignatureKeyPair() {
        return signatureKeyPair;
    }

    public List<Url> getDelegators() {
        return delegators;
    }

    public List<SignatureKeyPair> getAdditionalSignerSignatureKeyPairs() {
        return additionalSignerSignatureKeyPairs;
    }
}
