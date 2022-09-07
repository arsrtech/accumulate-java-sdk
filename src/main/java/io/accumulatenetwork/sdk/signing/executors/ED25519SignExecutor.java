package io.accumulatenetwork.sdk.signing.executors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iwebpp.crypto.TweetNaclFast;
import io.accumulatenetwork.sdk.generated.protocol.ED25519Signature;
import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.generated.protocol.VoteType;
import io.accumulatenetwork.sdk.protocol.Signature;
import io.accumulatenetwork.sdk.protocol.TransactionHasher;
import io.accumulatenetwork.sdk.protocol.Url;
import io.accumulatenetwork.sdk.support.HashBuilder;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;


public class ED25519SignExecutor implements SignExecutor, Cloneable {

    private ED25519Signature signatureModel;

    public ED25519SignExecutor(ED25519Signature signatureModel) {
        this.signatureModel = signatureModel;
    }

    ED25519SignExecutor(final byte[] publicKey, final byte[] signature, final Url signer,
                        final long signerVersion, final long timeStamp, final VoteType vote, final byte[] transactionHash) {
        this.signatureModel = new ED25519Signature()
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
    public ED25519SignExecutor getMetaData() {
        final ED25519SignExecutor clone = clone();
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
        final ByteBuffer buffer = ByteBuffer.allocate(txHash.length + metadataHash.length);
        buffer.put(metadataHash);
        buffer.put(txHash);
        final byte[] hash = sha256(buffer.array());
        TweetNaclFast.Signature naclSignature = new TweetNaclFast.Signature(getPublicKey(), privateKey);
        signatureModel.setSignature(naclSignature.detached(hash));
    }

    @Override
    public SignatureType getSignatureType() {
        return null;
    }

    public ED25519SignExecutor clone() {
        return new ED25519SignExecutor(
                getPublicKey(),
                getSigner(),
                signatureModel.getSigner(),
                signatureModel.getSignerVersion(),
                signatureModel.getTimestamp(),
                signatureModel.getVote(),
                signatureModel.getTransactionHash());
    }

    @Override
    public Signature getModel() {
        return signatureModel;
    }
}
