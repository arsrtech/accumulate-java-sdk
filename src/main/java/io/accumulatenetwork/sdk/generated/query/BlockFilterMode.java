package io.accumulatenetwork.sdk.generated.query;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.protocol.IntValueEnum;

public enum BlockFilterMode implements IntValueEnum {
    EXCLUDE_NONE(0, "excludeNone"),
    EXCLUDE_EMPTY(1, "excludeEmpty");

    private final int value;
    private final String apiName;

    BlockFilterMode(final int value, final String apiName) {
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

    public static BlockFilterMode fromValue(final int value) {
        for (final var type : values()) {
            if (value == type.ordinal()) {
                return type;
            }
        }
        throw new RuntimeException(String.format("%d is not a valid TransactionType", value));
    }

    public static BlockFilterMode fromApiName(final String name) {
        for (final var type : values()) {
            if (name != null && name.equalsIgnoreCase(type.apiName)) {
                return type;
            }
        }
        throw new RuntimeException(String.format("'%s' is not a valid TransactionType", name));
    }

    @JsonCreator
    public static BlockFilterMode fromJsonNode(final JsonNode jsonNode) {
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
