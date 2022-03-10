package com.sdk.accumulate.enums;

public enum TxType {

    /** Unknown represents an unknown transaction type. */
    Unknown (0),
    /** CreateIdentity creates an ADI, which produces a synthetic chain. */
    CreateIdentity (1),
    /** CreateTokenAccount creates an ADI token account, which produces a synthetic chain create transaction. */
    CreateTokenAccount (2),
    /** SendTokens transfers tokens between token accounts, which produces a synthetic deposit tokens transaction. */
    SendTokens (3),
    /** CreateDataAccount creates an ADI Data Account, which produces a synthetic chain create transaction. */
    CreateDataAccount (4),
    /** WriteData writes data to an ADI Data Account, which *does not* produce a synthetic transaction. */
    WriteData (5),
    /** WriteDataTo writes data to a Lite Data Account, which produces a synthetic write data transaction. */
    WriteDataTo (6),
    /** AcmeFaucet produces a synthetic deposit tokens transaction that deposits ACME tokens into a lite token account. */
    AcmeFaucet (7),
    /** CreateToken creates a token issuer, which produces a synthetic chain create transaction. */
    CreateToken (8),
    /** IssueTokens issues tokens to a token account, which produces a synthetic token deposit transaction. */
    IssueTokens (9),
    /** BurnTokens burns tokens from a token account, which produces a synthetic burn tokens transaction. */
    BurnTokens (10),
    /** CreateKeyPage creates a key page, which produces a synthetic chain create transaction. */
    CreateKeyPage (12),
    /** CreateKeyBook creates a key book, which produces a synthetic chain create transaction. */
    CreateKeyBook (13),
    /** AddCredits converts ACME tokens to credits, which produces a synthetic deposit credits transaction. */
    AddCredits (14),
    /** UpdateKeyPage adds, removes, or updates keys in a key page, which *does not* produce a synthetic transaction. */
    UpdateKeyPage (15),
    /** UpdateManager updates manager for the existing chain. */
    UpdateManager (16),
    /** RemoveManager remove manager from existing chain. */
    RemoveManager (17),
    /** SignPending is used to sign a pending transaction. */
    SignPending (48),
    /** SyntheticCreateChain creates or updates chains. */
    SyntheticCreateChain (49),
    /** SyntheticWriteData writes data to a data account. */
    SyntheticWriteData (50),
    /** SyntheticDepositTokens deposits tokens into token accounts. */
    SyntheticDepositTokens (51),
    /** SyntheticAnchor anchors one network to another. */
    SyntheticAnchor (52),
    /** SyntheticDepositCredits deposits credits into a credit holder. */
    SyntheticDepositCredits (53),
    /** SyntheticBurnTokens returns tokens to a token issuer's pool of issuable tokens. */
    SyntheticBurnTokens (54),
    /** SyntheticMirror mirrors records from one network to another. */
    SyntheticMirror (56),
    /** SegWitDataEntry is a surrogate transaction segregated witness for a WriteData transaction. */
    SegWitDataEntry (57),
    /** InternalGenesis initializes system chains. */
    InternalGenesis (96),
    /** InternalSendTransactions reserved for internal send. */
    InternalSendTransactions (97),
    /** InternalTransactionsSigned notifies the executor of synthetic transactions that have been signed. */
    InternalTransactionsSigned (98),
    /** InternalTransactionsSent notifies the executor of synthetic transactions that have been sent. */
    InternalTransactionsSent (99);

    private final int value;


    public int getValue() {
        return value;
    }

    TxType(int value) {

        this.value = value;
    }

}
