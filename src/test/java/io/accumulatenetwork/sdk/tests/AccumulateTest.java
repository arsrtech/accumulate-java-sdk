package io.accumulatenetwork.sdk.tests;

import io.accumulatenetwork.sdk.api.v2.AccumulateSyncApi;
import io.accumulatenetwork.sdk.api.v2.TransactionQueryResult;
import io.accumulatenetwork.sdk.api.v2.TransactionResult;
import io.accumulatenetwork.sdk.generated.apiv2.DataEntryQuery;
import io.accumulatenetwork.sdk.generated.apiv2.TxHistoryQuery;
import io.accumulatenetwork.sdk.generated.apiv2.TxnQuery;
import io.accumulatenetwork.sdk.generated.errors.Status;
import io.accumulatenetwork.sdk.generated.protocol.ADI;
import io.accumulatenetwork.sdk.generated.protocol.AccumulateDataEntry;
import io.accumulatenetwork.sdk.generated.protocol.AddCredits;
import io.accumulatenetwork.sdk.generated.protocol.CreateDataAccount;
import io.accumulatenetwork.sdk.generated.protocol.CreateIdentity;
import io.accumulatenetwork.sdk.generated.protocol.TransactionType;
import io.accumulatenetwork.sdk.generated.protocol.WriteData;
import io.accumulatenetwork.sdk.generated.protocol.WriteDataResult;
import io.accumulatenetwork.sdk.protocol.FactomEntry;
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
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccumulateTest extends AbstractTestBase {

    private AccumulateSyncApi accumulate = new AccumulateSyncApi();
    private TxID dataAccountTxId;

    @Test
    @Order(1)
    public void testFaucet() {
        final var txId = accumulate.faucet(liteAccount.getAccount().getUrl());
        waitForTx(txId);
    }

    @Test
    @Order(2)
    public void testAddCredits() {
        waitForAnchor();
        final var addCredits = new AddCredits()
                .recipient(liteAccount.getAccount().getUrl())
                .oracle(500)
                .amount(BigInteger.valueOf(22000000000L));
        final var transactionResult = accumulate.addCredits(liteAccount, addCredits);
        final var addCreditsResult = transactionResult.getResult();
        assertNotNull(addCreditsResult);
        assertTrue(addCreditsResult.getCredits() > 0);
        final var txQueryResult = waitForTx(transactionResult.getTxID());
        assertEquals(txQueryResult.getTxType(), TransactionType.ADD_CREDITS);
        final AddCredits data = ResultReader.readValue(txQueryResult.getQueryResponse().getData(), AddCredits.class);
        assertEquals(addCredits.getRecipient(), data.getRecipient());
        assertEquals(addCredits.getAmount(), data.getAmount());
        assertEquals(Status.DELIVERED, txQueryResult.getQueryResponse().getStatus().getCode());

        // TODO move to separate test
        var result = accumulate.getTxHistory(new TxHistoryQuery()
                .url(liteAccount.getAccount().getUrl())
                .start(1)
                .count(10));
        assertFalse(result.isEmpty());
    }


    @Test
    @Order(3)
    public void testCreateIdentity() {
        waitForAnchor();
        final CreateIdentity createIdentity = new CreateIdentity()
                .url(rootADI)
                .keyHash(liteAccount.getSignatureKeyPair().getPublicKeyHash())
                .keyBookUrl(rootADI + "/book");
        var txId = accumulate.createIdentity(liteAccount, createIdentity);
        System.out.println("CreateIdentity successful, TxID:" + txId);
        final var txQueryResult = waitForTx(txId);
        assertEquals(txQueryResult.getTxType(), TransactionType.CREATE_IDENTITY);
        final CreateIdentity data = ResultReader.readValue(txQueryResult.getQueryResponse().getData(), CreateIdentity.class);
        assertEquals(createIdentity.getUrl(), data.getUrl());
        assertEquals(createIdentity.getKeyBookUrl(), data.getKeyBookUrl());
        assertArrayEquals(createIdentity.getKeyHash(), data.getKeyHash());
        assertEquals(Status.DELIVERED, txQueryResult.getQueryResponse().getStatus().getCode());
    }

    @Test
    @Order(4)
    public void testAddCreditsToADI() {
        waitForAnchor();
        final String keyPageUrl = rootADI + "/book/1";
        final var addCredits = new AddCredits()
                .recipient(keyPageUrl)
                .oracle(500)
                .amount(BigInteger.valueOf(600000000L));
        final var transactionResult = accumulate.addCredits(liteAccount, addCredits);
        final var addCreditsResult = transactionResult.getResult();
        assertNotNull(addCreditsResult);
        assertTrue(addCreditsResult.getCredits() > 0);
        final var txQueryResult = waitForTx(transactionResult.getTxID());
        assertEquals(txQueryResult.getTxType(), TransactionType.ADD_CREDITS);
        final AddCredits data = ResultReader.readValue(txQueryResult.getQueryResponse().getData(), AddCredits.class);
        assertEquals(addCredits.getRecipient(), data.getRecipient());
        assertEquals(addCredits.getAmount(), data.getAmount());
        assertEquals(Status.DELIVERED, txQueryResult.getQueryResponse().getStatus().getCode());
    }


    @Test
    @Order(5)
    public void testCreateDataAccount() {
        waitForAnchor();
        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());
        this.dataAccountTxId = accumulate.createDataAccount(adiPrincipal,
                new CreateDataAccount()
                        .url(rootADI + "/data"));
        final var txQueryResult = waitForTx(dataAccountTxId);
        assertEquals(txQueryResult.getTxType(), TransactionType.CREATE_DATA_ACCOUNT);
    }

    @Test
    @Order(6)
    public void testWriteData() {
        waitForAnchor();
        final Principal dataPrincipal = new Principal(new ADI().url(rootADI + "/data"), liteAccount.getSignatureKeyPair());
        var writeDataResult = accumulate.writeData(dataPrincipal, new WriteData()
                .entry(new AccumulateDataEntry()
                        .data(buildDataRecord("entry1", "entry2")))
                .scratch(true));
        assertNotNull(writeDataResult.getResult());
        waitForTx(writeDataResult.getTxID());
    }

    @Test
    @Order(7)
    public void testQueryData() {
        waitForAnchor();
        var result = accumulate.queryData(new DataEntryQuery()
                .url(rootADI + "/data"));
        assertNotNull(result);
    }

    @Test
    @Order(8)
    public void testCreateLiteDataAccount() {
        waitForAnchor();
        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());
        TransactionResult<WriteDataResult> result = accumulate.createLiteDataAccount(adiPrincipal, new FactomEntry()
                .addExtRef("Factom PRO")
                .addExtRef("Tutorial"));
        final var txQueryResult = waitForTx(result.getTxID());
        assertEquals(txQueryResult.getTxType(), TransactionType.WRITE_DATA_TO);
        txQueryResult.getQueryResponse();
    }

    @Test
    @Order(9)
    public void testWriteDataTo() {
        waitForAnchor();
        final Principal dataPrincipal = new Principal(new ADI().url(rootADI + "/data"), liteAccount.getSignatureKeyPair());
        var writeDataResult = accumulate.writeData(dataPrincipal, new WriteData()
                .entry(new AccumulateDataEntry()
                        .data(buildDataRecord("entry1", "entry2")))
                .scratch(true));
        assertNotNull(writeDataResult.getResult());
        waitForTx(writeDataResult.getTxID());
    }

    private TransactionQueryResult waitForTx(final TxID txId) {
        final var result = new AtomicReference<TransactionQueryResult>();
        final var txnQuery = new TxnQuery()
                .txid(txId.getHash())
                .wait(Duration.ofMinutes(1));
        new Retry()
                .withTimeout(1, ChronoUnit.MINUTES)
                .withDelay(2, ChronoUnit.SECONDS)
                .withMessage("")
                .execute(() -> {
                    final var txQueryResult = accumulate.getTx(txnQuery);
                    assertNotNull(txQueryResult);
                    final var queryResponse = txQueryResult.getQueryResponse();
                    if (queryResponse.getStatus().getCode() == Status.PENDING) {
                        return true;
                    }
                    if (!queryResponse.getType().equalsIgnoreCase("syntheticCreateIdentity")) { // TODO syntheticCreateIdentity returns CONFLICT?
                        assertEquals(Status.DELIVERED, queryResponse.getStatus().getCode());
                    }
                    if (queryResponse.getProduced() != null) {
                        for (var producedTxId : queryResponse.getProduced()) {
                            waitForTx(producedTxId);
                        }
                    }
                    result.set(txQueryResult);
                    return false;
                });
        return result.get();
    }
}
