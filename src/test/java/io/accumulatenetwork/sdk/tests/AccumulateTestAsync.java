package io.accumulatenetwork.sdk.tests;

import io.accumulatenetwork.sdk.api.v2.AccumulateAsyncApi;
import io.accumulatenetwork.sdk.generated.apiv2.TransactionQueryResponse;
import io.accumulatenetwork.sdk.generated.apiv2.TxnQuery;
import io.accumulatenetwork.sdk.generated.errors.Status;
import io.accumulatenetwork.sdk.generated.protocol.ADI;
import io.accumulatenetwork.sdk.generated.protocol.AddCredits;
import io.accumulatenetwork.sdk.generated.protocol.AddCreditsResult;
import io.accumulatenetwork.sdk.generated.protocol.CreateDataAccount;
import io.accumulatenetwork.sdk.generated.protocol.CreateIdentity;
import io.accumulatenetwork.sdk.generated.protocol.TransactionType;
import io.accumulatenetwork.sdk.protocol.Principal;
import io.accumulatenetwork.sdk.protocol.TxID;
import io.accumulatenetwork.sdk.support.ResultReader;
import io.accumulatenetwork.sdk.support.Retry;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigInteger;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccumulateTestAsync extends AbstractTestBase {

    private AccumulateAsyncApi accumulate = new AccumulateAsyncApi();

    @Test
    @Order(1)
    public void testFaucet() throws Throwable {
        final var txIdRef = new AtomicReference<TxID>();
        final var exceptionRef = new AtomicReference<Throwable>();

        accumulate.faucet(liteAccount.getAccount().getUrl())
                .thenAccept(txResult -> txIdRef.set(txResult.getTxID()))
                .exceptionally(throwable -> {
                    exceptionRef.set(throwable);
                    return null;
                });

        await().atMost(10, TimeUnit.SECONDS).until(() -> txIdRef.get() != null || exceptionRef.get() != null);
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        assertNotNull(txIdRef.get());
        waitForTx(txIdRef.get());
    }


    @Test
    @Order(2)
    public void testAddCredits() throws Throwable {
        waitForAnchor();
        final var txIdRef = new AtomicReference<TxID>();
        final var resultRef = new AtomicReference<AddCreditsResult>();
        final var exceptionRef = new AtomicReference<Throwable>();
        final var addCredits = new AddCredits()
                .recipient(liteAccount.getAccount().getUrl())
                .oracle(500)
                .amount(BigInteger.valueOf(22000000000L));
        accumulate.addCredits(liteAccount, addCredits)
                .thenAccept(txResult -> {
                    txIdRef.set(txResult.getTxID());
                    resultRef.set(txResult.getResult());
                })
                .exceptionally(throwable -> {
                    exceptionRef.set(throwable);
                    return null;
                });

        await().atMost(10, TimeUnit.SECONDS).until(() -> txIdRef.get() != null || exceptionRef.get() != null);
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        assertNotNull(resultRef.get());
        assertTrue(resultRef.get().getCredits() > 0);

        waitForTx(txIdRef.get());
        final var txnQuery = new TxnQuery()
                .txid(txIdRef.get().getHash())
                .wait(Duration.ofMinutes(1));
        testGetTxAddCredits(txnQuery, addCredits);
    }

    private void testGetTxAddCredits(final TxnQuery txnQuery, final AddCredits addCredits) throws Throwable {
        waitForAnchor();

        final var responseRef = new AtomicReference<TransactionQueryResponse>();
        final var txTypeRef = new AtomicReference<TransactionType>();
        final var exceptionRef = new AtomicReference<Throwable>();

        accumulate.getTx(txnQuery)
                .thenAccept(txQueryResult -> {
                    responseRef.set(txQueryResult.getQueryResponse());
                    txTypeRef.set(txQueryResult.getTxType());
                })
                .exceptionally(throwable -> {
                    exceptionRef.set(throwable);
                    return null;
                });

        await().atMost(10, TimeUnit.SECONDS).until(() -> responseRef.get() != null || exceptionRef.get() != null);
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        final var txQueryResult = responseRef.get();
        assertEquals(txTypeRef.get(), TransactionType.ADD_CREDITS);
        final AddCredits data = ResultReader.readValue(txQueryResult.getData(), AddCredits.class);
        assertEquals(addCredits.getRecipient(), data.getRecipient());
        assertEquals(addCredits.getAmount(), data.getAmount());
        assertEquals(addCredits.getOracle(), data.getOracle());
    }


    @Test
    @Order(3)
    public void testCreateIdentity() throws Throwable {
        waitForAnchor();

        final var txIdRef = new AtomicReference<TxID>();
        final var exceptionRef = new AtomicReference<Throwable>();

        final CreateIdentity createIdentity = new CreateIdentity()
                .url(rootADI)
                .keyHash(liteAccount.getSignatureKeyPair().getPublicKeyHash())
                .keyBookUrl(rootADI + "/book");
        accumulate.createIdentity(liteAccount, createIdentity)
                .thenAccept(txResult -> {
                    txIdRef.set(txResult.getTxID());
                })
                .exceptionally(throwable -> {
                    exceptionRef.set(throwable);
                    return null;
                });

        await().atMost(10, TimeUnit.SECONDS).until(() -> txIdRef.get() != null || exceptionRef.get() != null);
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        assertNotNull(txIdRef.get());
        waitForTx(txIdRef.get());
    }

    @Test
    @Order(4)
    public void testAddCreditsToADI() throws Throwable {
        waitForAnchor();

        final var txIdRef = new AtomicReference<TxID>();
        final var resultRef = new AtomicReference<AddCreditsResult>();
        final var exceptionRef = new AtomicReference<Throwable>();

        final String keyPageUrl = rootADI + "/book/1";
        final var addCredits = new AddCredits()
                .recipient(keyPageUrl)
                .oracle(500)
                .amount(BigInteger.valueOf(600000000L));
        accumulate.addCredits(liteAccount, addCredits)
                .thenAccept(txResult -> {
                    txIdRef.set(txResult.getTxID());
                    resultRef.set(txResult.getResult());
                })
                .exceptionally(throwable -> {
                    exceptionRef.set(throwable);
                    return null;
                });

        await().atMost(10, TimeUnit.SECONDS).until(() -> txIdRef.get() != null || exceptionRef.get() != null);
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        assertNotNull(resultRef.get());
        waitForTx(txIdRef.get());
    }


    @Test
    @Order(5)
    public void testDataAccount() throws Throwable {
        waitForAnchor();

        final var txIdRef = new AtomicReference<TxID>();
        final var exceptionRef = new AtomicReference<Throwable>();

        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());
        accumulate.createDataAccount(adiPrincipal, new CreateDataAccount()
                        .url(rootADI + "/data"))
                .thenAccept(txResult -> {
                    txIdRef.set(txResult.getTxID());
                })
                .exceptionally(throwable -> {
                    exceptionRef.set(throwable);
                    return null;
                });

        await().atMost(10, TimeUnit.SECONDS).until(() -> txIdRef.get() != null || exceptionRef.get() != null);
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        waitForTx(txIdRef.get());
    }


    private void waitForTx(final TxID txID) throws Throwable {
        final var txnQuery = new TxnQuery()
                .txid(txID.getHash())
                .wait(Duration.ofMinutes(1));

        final var responseRef = new AtomicReference<TransactionQueryResponse>();
        final var txTypeRef = new AtomicReference<TransactionType>();
        final var exceptionRef = new AtomicReference<Throwable>();
        final var txQueryResponseRef = new AtomicReference<TransactionQueryResponse>();
        new Retry()
                .withTimeout(1, ChronoUnit.MINUTES)
                .withDelay(2, ChronoUnit.SECONDS)
                .withMessage("Transaction was still pending after %s")
                .execute(() -> {
                    accumulate.getTx(txnQuery)
                            .thenAccept(txQueryResult -> {
                                responseRef.set(txQueryResult.getQueryResponse());
                                txTypeRef.set(txQueryResult.getTxType());
                            })
                            .exceptionally(throwable -> {
                                exceptionRef.set(throwable);
                                return null;
                            });

                    await().atMost(61, TimeUnit.SECONDS).until(() -> responseRef.get() != null || exceptionRef.get() != null);
                    if (exceptionRef.get() != null) {
                        return false;
                    }
                    final var txQueryResult = responseRef.get();
                    assertNotNull(txQueryResult);
                    assertNotNull(txQueryResult.getStatus());
                    if (txQueryResult.getStatus().getCode() == Status.PENDING) {
                        return true;
                    }
                    txQueryResponseRef.set(txQueryResult);
                    return false;
                });
        if (exceptionRef.get() != null) {
            throw exceptionRef.get();
        }
        if (txQueryResponseRef.get().getProduced() != null) {
            for (TxID producedTxId : txQueryResponseRef.get().getProduced()) {
                waitForTx(producedTxId);
            }
        }
    }
}
