package com.sdk.accumulate.enums;

public enum RequestMethods {
    AddCredits("add-credits"),
    BurnTokens("burn-tokens"),
    CreateDataAccount("create-data-account"),
    CreateIdentity("create-adi");

    private final String value;

    public String getValue() {
        return value;
    }

    RequestMethods(String value) {
        this.value = value;
    }
}
