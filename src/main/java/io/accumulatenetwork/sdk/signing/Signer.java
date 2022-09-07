package io.accumulatenetwork.sdk.signing;

import io.accumulatenetwork.sdk.generated.protocol.ED25519Signature;
import io.accumulatenetwork.sdk.generated.protocol.RCD1Signature;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.protocol.InitHashMode;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.signing.executors.DelegatedSignExecutor;
import io.accumulatenetwork.sdk.signing.executors.ED25519SignExecutor;
import io.accumulatenetwork.sdk.signing.executors.RCD1SignExecutor;
import io.accumulatenetwork.sdk.signing.executors.SignExecutor;
import io.accumulatenetwork.sdk.support.HashBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;


public class Signer {

    private InitHashMode initMode = InitHashMode.INIT_WITH_MERKLE_HASH;
    private SignatureType signatureType;
    private Url url;
    private Integer version;
    private final List<Url> delegators = new ArrayList<>();
    private long timestamp;
    private byte[] privateKey;


    Signer() {
    }

    public Signer withType(final SignatureType signatureType) {
        this.signatureType = signatureType;
        return this;
    }


    public Signer withTimeStamp(final long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Signer withNonceFromTimeNow() {
        this.timestamp = System.currentTimeMillis() * 1000 + (System.nanoTime() % 1000);
        return this;
    }


    public Signer withUseSimpleHash() {
        this.initMode = InitHashMode.INIT_WITH_SIMPLE_HASH;
        return this;
    }

    public Signer withUseMerkleHash() {
        this.initMode = InitHashMode.INIT_WITH_MERKLE_HASH;
        return this;
    }

    public Signer withVersion(final Integer version) {
        this.version = version;
        return this;
    }

    public Signer withSignerPrivateKey(final byte[] signer) {
        this.privateKey = signer;
        return this;
    }

    public Signer withDelegators(final List<Url> delegators) {
        this.delegators.addAll(delegators);
        return this;
    }

    public Signer withUrl(final Url url) {
        this.url = url;
        return this;
    }

    public SignExecutor prepare(final boolean init) {
        validate(init);
        switch (this.signatureType) {
            case ED25519:
                return new ED25519SignExecutor(new ED25519Signature()
                        .signer(url)
                        .signerVersion(version)
                        .timestamp(timestamp)
                        .publicKey(getPublicKey()));
            case RCD1:
                return new RCD1SignExecutor(new RCD1Signature()
                        .signer(url)
                        .signerVersion(version)
                        .timestamp(timestamp)
                        .publicKey(getPublicKey()));
        }
        throw new IllegalArgumentException(String.format("Signature type %s is not supported by prepare.", signatureType.name()));
    }

    private byte[] getPublicKey() {
        return Arrays.copyOfRange(privateKey, 32, 64);
    }

    private void validate(final boolean init) {
        Objects.requireNonNull(url, "missing signer URL");
        Objects.requireNonNull(privateKey, "missing signer private key");
        Objects.requireNonNull(signatureType, "missing signatureType");
        if (init) {
            Objects.requireNonNull(version, "missing version");
            Objects.requireNonNull(timestamp, "missing timestamp");
        }
    }

    public SignExecutor initiate(final Transaction transaction) {
        if (transaction.getHeader() == null) {
            throw new RuntimeException("Transaction must have a header");
        }

        SignExecutor signExecutor = prepare(true);
        for (final Url delegator : delegators) {
            signExecutor = DelegatedSignExecutor.fromSigner(signExecutor, delegator);
        }
        final SignExecutor metaData = signExecutor.getMetaData();
        final byte[] metadataHash = sha256(metaData.marshalBinary());
        switch (initMode) {
            case INIT_WITH_MERKLE_HASH:
                HashBuilder hashBuilder = signExecutor.initiator();
                transaction.getHeader().setInitiator(hashBuilder.merkleHash());
                break;
            case INIT_WITH_SIMPLE_HASH:
                transaction.getHeader().setInitiator(metadataHash);
                break;
        }
        signExecutor.sign(transaction, metadataHash, privateKey);
        return signExecutor;
    }
}
