package io.accumulatenetwork.sdk.protocol;

import io.accumulatenetwork.sdk.generated.protocol.Envelope;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.generated.protocol.TransactionHeader;
import io.accumulatenetwork.sdk.signing.SignersPreparer;

public class EnvelopeBuilder {

    private final Envelope envelope;

    public EnvelopeBuilder(final Url principalUrl, final SignersPreparer signersPreparer, final TransactionBody body) {
        envelope = new Envelope();

        final Transaction tx = new Transaction()
                .header(new TransactionHeader()
                        .principal(principalUrl))
                .body(body);
        envelope.setTransaction(new Transaction[]{tx});

        signersPreparer.prepareSigners().forEach(signer -> {
            final var signExecutor = signer.initiate(tx);
            envelope.setSignatures(new Signature[]{signExecutor.getModel()});
        });
    }

    public Envelope getEnvelope() {
        return envelope;
    }
}
