package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.protocol.IntValueEnum;

public enum AccountType implements IntValueEnum {
    UNKNOWN(0, "unknown"),
    ANCHOR_LEDGER(1, "anchorLedger"),
    IDENTITY(2, "identity"),
    TOKEN_ISSUER(3, "tokenIssuer"),
    TOKEN_ACCOUNT(4, "tokenAccount"),
    LITE_TOKEN_ACCOUNT(5, "liteTokenAccount"),
    KEY_PAGE(9, "keyPage"),
    KEY_BOOK(10, "keyBook"),
    DATA_ACCOUNT(11, "dataAccount"),
    LITE_DATA_ACCOUNT(12, "liteDataAccount"),
    UNKNOWN_SIGNER(13, "unknownSigner"),
    SYSTEM_LEDGER(14, "systemLedger"),
    LITE_IDENTITY(15, "liteIdentity"),
    SYNTHETIC_LEDGER(16, "syntheticLedger");

    private final int value;
    private final String apiName;

    AccountType(final int value, final String apiName) {
        this.value = value;
        this.apiName = apiName;
    }

    public int getValue() {
        return this.value;
    }

    @JsonValue
    public String getApiName() {
        return this.apiName;
    }

    public String toString() {
        return this.apiName;
    }

    public static AccountType fromValue(final int value) {
        for (final var type : values()) {
            if (value == type.ordinal()) {
                return type;
            }
        }
        throw new RuntimeException(String.format("%d is not a valid TransactionType", value));
    }

    public static AccountType fromApiName(final String name) {
        for (final var type : values()) {
            if (name != null && name.equalsIgnoreCase(type.apiName)) {
                return type;
            }
        }
        throw new RuntimeException(String.format("'%s' is not a valid TransactionType", name));
    }

    @JsonCreator
    public static AccountType fromJsonNode(final JsonNode jsonNode) {
        for (final var type : values()) {
            if (jsonNode.isTextual() && jsonNode.asText().equalsIgnoreCase(type.apiName)) {
                return type;
            }
            if (jsonNode.isNumber() && jsonNode.asInt() == type.ordinal()) {
                return type;
            }
        }
        throw new RuntimeException(String.format("'%s' is not a valid TransactionType", jsonNode.toPrettyString()));
    }
}