package com.sdk.accumulate.enums;

public enum RequestMethods {
    AddCredits("add-credits"),
    BurnTokens("burn-tokens"),
    CreateDataAccount("create-data-account"),
    CreateIdentity("create-adi"),
    CreateKeyBook("create-key-book"),
    CreateKeyPage("create-key-page"),
    CreateTokenAccount("create-token-account"),
    CreateToken("create-token"),
    IssueToken("issue-token"),
    SendToken("send-token"),
    UpdateKeyPage("update-key-page"),
    WriteData("write-data");
    private final String value;

    public String getValue() {
        return value;
    }

    RequestMethods(String value) {
        this.value = value;
    }
}
