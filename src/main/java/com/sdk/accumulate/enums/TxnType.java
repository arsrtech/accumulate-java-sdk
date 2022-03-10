package com.sdk.accumulate.enums;

public enum TxnType {
    AddCredits("addCredits"),
    BurnTokens("burnTokens"),
    CreateDataAccount("createDataAccount"),
    CreateIdentity("createIdentity"),
    CreateKeyBook("createKeyBook"),
    CreateKeyPage("createKeyPage"),
    CreateTokenAccount("createTokenAccount"),
    CreateToken("createToken"),
    IssueToken("issueToken"),
    SendToken("sendToken"),
    UpdateKeyPage("updateKeyPage"),
    WriteData("writeData");

    private final String value;

    public String getValue() {
        return value;
    }

    TxnType(String value) {
        this.value = value;
    }
}
