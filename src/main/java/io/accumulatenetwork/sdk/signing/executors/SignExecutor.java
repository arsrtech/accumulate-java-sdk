package io.accumulatenetwork.sdk.signing.executors;

import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.protocol.Signature;
import io.accumulatenetwork.sdk.support.HashBuilder;

public interface SignExecutor {

    byte[] getPublicKey();

    byte[] getSigner();

    byte[] getTransactionHash();

    SignExecutor getMetaData();

    HashBuilder initiator();

    void sign(Transaction transaction, final byte[] metadataHash, final byte[] privateKey);

    SignatureType getSignatureType();

    byte[] marshalBinary();

    Signature getModel();
}
