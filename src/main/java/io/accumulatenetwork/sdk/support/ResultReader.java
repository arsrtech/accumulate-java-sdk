package io.accumulatenetwork.sdk.support;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.accumulatenetwork.sdk.generated.apiv2.TxResponse;
import io.accumulatenetwork.sdk.generated.protocol.TransactionStatus;
import io.accumulatenetwork.sdk.generated.protocol.TransactionType;

import java.io.IOException;

public class ResultReader {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
    private static final ObjectReader objectReader = objectMapper.reader();


    public static <T> T readValue(final String json, final Class<?> valueType) {
        try {
            return (T) objectReader.readValue(json, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(final JsonNode node, final Class<?> valueType) {
        return (T) objectMapper.convertValue(node, valueType);
    }

    public static void checkForErrors(final TxResponse txResponse) {
        if (txResponse == null) {
            throw new RuntimeException("No transaction response");
        }
        if (txResponse.getCode() != 0) {
            throw new RuntimeException(String.format("Transaction error: %d - %s", txResponse.getCode(), txResponse.getMessage()));
        }
    }

    public static void checkForErrors(final TxResponse txResponse, final TransactionStatus txStatus) {
        if (txResponse == null) {
            throw new RuntimeException("No transaction response");
        }
        if (txStatus == null) {
            checkForErrors(txResponse);
        }

        RuntimeException exception = null;
        final var error = txStatus.getError();
        if (error != null) {
            exception = new RuntimeException(String.format("Transaction status error: %d - %s", error.getCode().getValue(), error.getMessage()));
        }
        if (txResponse.getCode() != 0) {
            exception = new RuntimeException(String.format("Transaction error: %d - %s", txResponse.getCode(), txResponse.getMessage()), exception);
        }
        if (exception != null)
            throw exception;
    }

    public static TransactionType getTransactionType(final JsonNode jsonNode) {
        if (jsonNode.isTextual() || jsonNode.isNumber()) {
            return TransactionType.fromJsonNode(jsonNode);
        }
        if (jsonNode.isObject() && jsonNode.has("type")) {
            return TransactionType.fromJsonNode(jsonNode.get("type"));
        }
        throw new RuntimeException(String.format("Can't determine a transaction type from '%s' ", jsonNode.toPrettyString()));
    }
}
