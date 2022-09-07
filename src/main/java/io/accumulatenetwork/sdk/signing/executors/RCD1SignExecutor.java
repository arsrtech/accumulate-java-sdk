package io.accumulatenetwork.sdk.signing.executors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iwebpp.crypto.TweetNaclFast;
import io.accumulatenetwork.sdk.generated.protocol.RCD1Signature;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.generated.protocol.VoteType;
import io.accumulatenetwork.sdk.protocol.TransactionHasher;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.HashBuilder;
import io.accumulatenetwork.sdk.support.HashUtils;
import io.accumulatenetwork.sdk.support.Marshaller;

import java.math.BigInteger;
import java.nio.ByteBuffer;


public class RCD1SignExecutor implements SignExecutor, Cloneable {

    private RCD1Signature signatureModel;

    public RCD1SignExecutor(RCD1Signature signatureModel) {
        this.signatureModel = signatureModel;
    }

    RCD1SignExecutor(final byte[] publicKey, final byte[] signature, final Url signer,
                     final long signerVersion, final long timeStamp, final VoteType vote, final byte[] transactionHash) {
        this.signatureModel = new RCD1Signature()
                .publicKey(publicKey)
                .signature(signature)
                .transactionHash(transactionHash)
                .signer(signer)
                .signerVersion(signerVersion)
                .timestamp(timeStamp)
                .vote(vote);
    }

    @Override
    public byte[] getPublicKey() {
        return signatureModel.getPublicKey();
    }

    @Override
    public byte[] getSigner() {
        return signatureModel.getSignature();
    }

    @Override
    public byte[] getTransactionHash() {
        return signatureModel.getTransactionHash();
    }

    @Override
    @JsonIgnore
    public RCD1SignExecutor getMetaData() {
        final RCD1SignExecutor clone = clone();
        clone.signatureModel.setSignature(null);
        clone.signatureModel.setTransactionHash(null);
        return clone;
    }

    @Override
    public HashBuilder initiator() {
        return new HashBuilder()
                .addBytes(getPublicKey())
                .addUrl(signatureModel.getSigner())
                .addUInt(BigInteger.valueOf(signatureModel.getSignerVersion()))
                .addUInt(BigInteger.valueOf(signatureModel.getTimestamp()));
    }

    @Override
    public byte[] marshalBinary() {
        return signatureModel.marshalBinary();
    }

    @Override
    public void sign(final Transaction transaction, final byte[] metadataHash, final byte[] privateKey) {
        final Transaction hashedTx = TransactionHasher.hashTransaction(transaction);
        final byte[] txHash = hashedTx.gethash();
        signatureModel.setTransactionHash(txHash);
        final ByteBuffer buffer = ByteBuffer.allocate(txHash.length + metadataHash.length + 8);
        buffer.put(metadataHash);
        buffer.put(Marshaller.toUIntBuffer(signatureModel.getTimestamp()));
        buffer.put(txHash);
        final byte[] hash = HashUtils.sha256(buffer.array());
        TweetNaclFast.Signature naclSignature = new TweetNaclFast.Signature(getPublicKey(), privateKey);
        signatureModel.setSignature(naclSignature.detached(hash));
    }

    @Override
    public SignatureType getSignatureType() {
        return null;
    }

    public RCD1SignExecutor clone() {
        return new RCD1SignExecutor(
                getPublicKey(),
                getSigner(),
                signatureModel.getSigner(),
                signatureModel.getSignerVersion(),
                signatureModel.getTimestamp(),
                signatureModel.getVote(),
                signatureModel.getTransactionHash());
    }

    public RCD1Signature getModel() {
        return signatureModel;
    }
}
