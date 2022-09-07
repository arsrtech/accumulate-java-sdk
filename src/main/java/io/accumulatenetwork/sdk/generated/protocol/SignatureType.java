package io.accumulatenetwork.sdk.generated.protocol;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.protocol.IntValueEnum;

public enum SignatureType implements IntValueEnum {
    UNKNOWN(0, "unknown"),
    LEGACY_ED25519(1, "legacyED25519"),
    ED25519(2, "ed25519"),
    RCD1(3, "rcd1"),
    RECEIPT(4, "receipt"),
    PARTITION(5, "partition"),
    SET(6, "set"),
    REMOTE(7, "remote"),
    BTC(8, "btc"),
    BTCLEGACY(9, "btclegacy"),
    ETH(10, "eth"),
    DELEGATED(11, "delegated"),
    INTERNAL(12, "internal");

    private final int value;
    private final String apiName;

    SignatureType(final int value, final String apiName) {
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

    public static SignatureType fromValue(final int value) {
        for (final var type : values()) {
            if (value == type.ordinal()) {
                return type;
            }
        }
        throw new RuntimeException(String.format("%d is not a valid TransactionType", value));
    }

    public static SignatureType fromApiName(final String name) {
        for (final var type : values()) {
            if (name != null && name.equalsIgnoreCase(type.apiName)) {
                return type;
            }
        }
        throw new RuntimeException(String.format("'%s' is not a valid TransactionType", name));
    }

    @JsonCreator
    public static SignatureType fromJsonNode(final JsonNode jsonNode) {
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