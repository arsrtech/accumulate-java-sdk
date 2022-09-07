package io.accumulatenetwork.sdk.tests;

import io.accumulatenetwork.sdk.api.v2.AccumulateSyncApi;
import io.accumulatenetwork.sdk.api.v2.TransactionQueryResult;
import io.accumulatenetwork.sdk.generated.apiv2.*;
import io.accumulatenetwork.sdk.generated.errors.Status;
import io.accumulatenetwork.sdk.generated.protocol.*;
import io.accumulatenetwork.sdk.protocol.LiteAccount;
import io.accumulatenetwork.sdk.protocol.Principal;
import io.accumulatenetwork.sdk.protocol.TxID;
import io.accumulatenetwork.sdk.support.ResultReader;
import io.accumulatenetwork.sdk.support.Retry;
import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.net.URI;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class E2ETest extends AbstractTestBase {

    private final AccumulateSyncApi accumulate = new AccumulateSyncApi(URI.create("http://127.0.1.1:26660/v2"));

    private TxID queryTxnTxId;
    private TxID dataAccountTxId;

    @Test
    @Order(1)
    public void testFaucet() {
        final var txId = accumulate.faucet(liteAccount.getAccount().getUrl());
        waitForTx(txId);
    }

    @Test
    @Order(2)
    public void testGetLiteAccount() {
        waitForAnchor();
        final var response = accumulate.queryUrl(
                new GeneralQuery()
                        .url(liteAccount.getAccount().getUrl()));
        assertNotNull(response);
    }

    @Test
    @Order(3)
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
        queryTxnTxId = transactionResult.getTxID();
        final var txQueryResult = waitForTx(transactionResult.getTxID());
        assertEquals(txQueryResult.getTxType(), TransactionType.ADD_CREDITS);
        final AddCredits data = ResultReader.readValue(txQueryResult.getQueryResponse().getData(), AddCredits.class);
        assertEquals(addCredits.getRecipient(), data.getRecipient());
        assertEquals(addCredits.getAmount(), data.getAmount());
        assertEquals(Status.DELIVERED, txQueryResult.getQueryResponse().getStatus().getCode());

        var result = accumulate.getTxHistory(new TxHistoryQuery()
                .url(liteAccount.getAccount().getUrl())
                .start(1)
                .count(10));
        assertFalse(result.isEmpty());
    }

    @Test
    @Order(4)
    public void testGetHistory() {
        waitForAnchor();
        final var txn = new TxHistoryQuery()
                .url(liteAccount.getAccount().getUrl())
                .start(1)
                .count(10);
        var txnResult = accumulate.getTxHistory(txn);
        assertFalse(txnResult.isEmpty());
    }

    @Test
    @Order(5)
    public void testGetTransaction() {
        waitForAnchor();
        final var txn = new TxnQuery()
                .txid(queryTxnTxId.getHash());
        var txnResult = accumulate.getTx(txn);
        System.out.println(txnResult);
        assertNotNull(txnResult);
    }

    @Test
    @Order(6)
    public void testCreateIdentity() {
        waitForAnchor();
        final CreateIdentity createIdentity = new CreateIdentity()
                .url(rootADI)
                .keyHash(liteAccount.getSignatureKeyPair().getPublicKeyHash())
                .keyBookUrl(rootADI + "/book");
        var txId = accumulate.createIdentity(liteAccount, createIdentity);
        final var txQueryResult = waitForTx(txId);
        assertEquals(txQueryResult.getTxType(), TransactionType.CREATE_IDENTITY);
        final CreateIdentity data = ResultReader.readValue(txQueryResult.getQueryResponse().getData(), CreateIdentity.class);
        assertEquals(createIdentity.getUrl(), data.getUrl());
        assertEquals(createIdentity.getKeyBookUrl(), data.getKeyBookUrl());
        assertArrayEquals(createIdentity.getKeyHash(), data.getKeyHash());
        assertEquals(Status.DELIVERED, txQueryResult.getQueryResponse().getStatus().getCode());
    }

    @Test
    @Order(7)
    public void testGetADI() {
        waitForAnchor();
        final var response = accumulate.queryUrl(
                new GeneralQuery()
                        .url(rootADI));
        assertNotNull(response);
    }

    @Test
    @Order(8)
    public void testAddCreditsToADI() {
        waitForAnchor();
        final String keyPageUrl = rootADI + "/book/1";
        final var addCredits = new AddCredits()
                .recipient(keyPageUrl)
                .oracle(500)
                .amount(BigInteger.valueOf(60000000000000L));
        final var transactionResult = accumulate.addCredits(liteAccount, addCredits);
        final var addCreditsResult = transactionResult.getResult();
        System.out.println("Add Credits result");
        System.out.println(addCreditsResult.getCredits());
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
    @Order(9)
    public void testCreateKeyBook() {
        waitForAnchor();
        final CreateKeyBook createKeyBook = new CreateKeyBook()
                .url(rootADI+"/testKeybook")
                .publicKeyHash(liteAccount.getSignatureKeyPair().getPublicKeyHash());
        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());
        var txId = accumulate.createKeyBook(adiPrincipal, createKeyBook);
        System.out.println("CreateIdentity successful, TxID:" + txId);
        final var txQueryResult = waitForTx(txId);
        assertEquals(txQueryResult.getTxType(), TransactionType.CREATE_KEY_BOOK);
        final CreateKeyBook data = ResultReader.readValue(txQueryResult.getQueryResponse().getData(), CreateKeyBook.class);
        assertEquals(createKeyBook.getUrl(), data.getUrl());
        assertEquals(Status.DELIVERED, txQueryResult.getQueryResponse().getStatus().getCode());
    }

//    @Test
//    @Order(10)
//    public void testCreateKeyPage() {
//        waitForAnchor();
//        KeySpecParams keySpecParams = new KeySpecParams();
//        keySpecParams.setKeyHash(AccKeyPairGenerator.generate(SignatureType.ED25519).getPublicKey());
//        keySpecParams.setDelegate(Url.toAccURL(rootADI+"/testKeybook"));
//        KeySpecParams[] keySpecParams1 = {keySpecParams};
//        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());
//        final CreateKeyPage createKeyPage = new CreateKeyPage().keys(keySpecParams1);
//        var txId = accumulate.createKeyPage(adiPrincipal, createKeyPage);
//        System.out.println(txId);
//    }

    @Test
    @Order(11)
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
    @Order(12)
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
    @Order(13)
    public void testQueryData() {
        waitForAnchor();
        var result = accumulate.queryData(new DataEntryQuery()
                .url(rootADI + "/data"));
        assertNotNull(result);
    }

//    @Test
//    @Order(14)
//    public void testQueryKeyIndex() {
//        waitForAnchor();
//        var result = accumulate.queryKeyIndex(
//                new KeyPageIndexQuery()
//                        .url(rootADI)
//                        .key(""));
//        assertNotNull(result);
//    }

    @Test
    @Order(15)
    public void createTokenAccountTest() {
        waitForAnchor();
        final CreateTokenAccount createTokenAccount = new CreateTokenAccount()
                .tokenUrl("acc://ACME")
                .url(rootADI+"/tokenAcc");
        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());
        var txId = accumulate.createTokenAccount(adiPrincipal, createTokenAccount);
        final var txQueryResult = waitForTx(txId);
        assertEquals(txQueryResult.getTxType(), TransactionType.CREATE_TOKEN_ACCOUNT);
    }

    @Test
    @Order(16)
    public void testCreateTokens() {
        waitForAnchor();
        final CreateToken createToken = new CreateToken()
                .url(rootADI+"/tokenIssuer")
                .symbol("ADT")
                .precision(2)
                .supplyLimit(BigInteger.valueOf(10000000000L));
        final Principal adiPrincipal = new Principal(new ADI().url(rootADI), liteAccount.getSignatureKeyPair());

        var txId = accumulate.createAccount(adiPrincipal, createToken);
        final var txQueryResult = waitForTx(txId);
        assertEquals(txQueryResult.getTxType(), TransactionType.CREATE_TOKEN);
    }

//    @Test
//    @Order(17)
//    public void testSendTokens() {
//        waitForAnchor();
//        LiteAccount liteAccount1 = LiteAccount.generate(SignatureType.ED25519);
//        final var txId = accumulate.faucet(liteAccount1.getAccount().getUrl());
//        TokenRecipient[] tokenRecipient = {
//                new TokenRecipient()
//                        .url(liteAccount1.getAccount().getUrl())
//                        .amount(BigInteger.valueOf(6L))
//        };
//
//        final SendTokens sendTokens = new SendTokens()
//                .to(tokenRecipient);
//
//        var txId1 = accumulate.sendTokens(liteAccount, sendTokens);
//        System.out.println("Send tokens" + txId);
//    }

//    @Test
//    @Order(18)
//    public void testBurnTokens() {
//        waitForAnchor();
//        final BurnTokens burnTokens = new BurnTokens()
//                .amount(BigInteger.valueOf(60000L));
//        var txId = accumulate.burnTokens(liteAccount, burnTokens);
//        System.out.println("Burn tokens" + txId);
//    }

    @Test
    @Order(19)
    public void testIssueTokens() {
        waitForAnchor();
        LiteAccount liteAccount1 = LiteAccount.generate(SignatureType.ED25519);
        final var txId = accumulate.faucet(liteAccount1.getAccount().getUrl());
        waitForAnchor();
        final IssueTokens issueTokens = new IssueTokens()
                .amount(BigInteger.valueOf(100L))
                .recipient(rootADI+"/tokenAcc");
        final Principal adiPrincipal = new Principal(new ADI().url(rootADI+"/tokenIssuer"), liteAccount.getSignatureKeyPair());
        var txId1 = accumulate.issueTokens(adiPrincipal, issueTokens);
        System.out.println("Send tokens" + txId1);
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
