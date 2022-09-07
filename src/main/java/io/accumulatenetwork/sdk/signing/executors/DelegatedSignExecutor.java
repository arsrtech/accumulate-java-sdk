package io.accumulatenetwork.sdk.signing.executors;

import io.accumulatenetwork.sdk.generated.protocol.DelegatedSignature;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.protocol.Signature;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.HashBuilder;

import java.util.concurrent.atomic.AtomicReference;

import static io.accumulatenetwork.sdk.support.Marshaller.is;
import static io.accumulatenetwork.sdk.support.Marshaller.when;


public class DelegatedSignExecutor implements SignExecutor, Cloneable {

    private DelegatedSignature signatureModel;

    private SignExecutor signExecutor;


    public DelegatedSignExecutor(final ED25519SignExecutor signer, final Url delegator) {
        this.signExecutor = signer;
        this.signatureModel = new DelegatedSignature();
        setDelegator(delegator);
    }

    public DelegatedSignExecutor(final RCD1SignExecutor signer, final Url delegator) {
        this.signExecutor = signer;
        this.signatureModel = new DelegatedSignature();
        setDelegator(delegator);
    }

    public static DelegatedSignExecutor fromSigner(final SignExecutor signExecutor, final Url delegator) {
        switch (signExecutor.getSignatureType()) {
            case ED25519:
                return new DelegatedSignExecutor((ED25519SignExecutor) signExecutor, delegator);
            case RCD1:
                return new DelegatedSignExecutor((RCD1SignExecutor) signExecutor, delegator);
        }
        throw new IllegalArgumentException(String.format("Signature type %s is not supported.", signExecutor.getSignatureType().name()));
    }

    public Url getDelegator() {
        return signatureModel.getDelegator();
    }

    public void setDelegator(Url delegator) {
        signatureModel.setDelegator(delegator);
    }

    @Override
    public byte[] getPublicKey() {
        return signExecutor.getPublicKey();
    }

    @Override
    public byte[] getSigner() {
        return signExecutor.getSigner();
    }

    @Override
    public byte[] getTransactionHash() {
        return signExecutor.getTransactionHash();
    }

    @Override
    public SignExecutor getMetaData() {
        return signExecutor.getMetaData();
    }

    @Override
    public HashBuilder initiator() {
        return signExecutor.initiator();
    }

    @Override
    public byte[] marshalBinary() {
        return signatureModel.marshalBinary();
    }

    @Override
    public Signature getModel() {
        return signExecutor.getModel();
    }

    @Override
    public void sign(final Transaction transactionHash, final byte[] metadataHash, final byte[] privateKey) {
        signExecutor.sign(transactionHash, metadataHash, privateKey);
    }

    @Override
    public SignatureType getSignatureType() {
        return null;
    }


    public DelegatedSignExecutor clone() {
        final AtomicReference<DelegatedSignExecutor> clone = new AtomicReference<>();
        when(signExecutor.getClass(),
                is(ED25519SignExecutor.class, v ->
                        clone.set(new DelegatedSignExecutor((ED25519SignExecutor) signExecutor, getDelegator()))),
                is(RCD1SignExecutor.class, v ->
                        clone.set(new DelegatedSignExecutor((RCD1SignExecutor) signExecutor, getDelegator())))
        );
        return fromSigner(clone.get(), this.getDelegator());
    }
}
