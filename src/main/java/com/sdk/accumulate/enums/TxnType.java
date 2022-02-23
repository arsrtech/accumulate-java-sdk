package com.sdk.accumulate.enums;

public enum TxnType {
    AddCredits("addCredits"),
    BurnTokens("burnTokens"),
    CreateDataAccount("createDataAccount"),
    CreateIdentity("createIdentity"),
    CreateKeyPage("createKeyPage");

    private final String value;

    public String getValue() {
        return value;
    }

    TxnType(String value) {
        this.value = value;
    }
}
