package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import io.accumulatenetwork.sdk.generated.query.ResponseKeyPageIndex;

public enum QueryResponseType {
    KEY_PAGE_INDEX("key-page-index", ResponseKeyPageIndex.class),

    ;
    private String responseType;
    private final Class requestClass;

    QueryResponseType(String responseType, Class requestClass) {
        this.responseType = responseType;
        this.requestClass = requestClass;
    }

    public String getResponseType() {
        return responseType;
    }

    public Class getRequestClass() {
        return requestClass;
    }

    public static QueryResponseType fromClass(final Class aClass) {
        for (final var method : values()) {
            if (method.requestClass != null && method.requestClass.equals(aClass)) {
                return method;
            }
        }
        throw new IllegalArgumentException("No Query Response Type found for class " + aClass.getName());
    }

    public static QueryResponseType fromJsonNode(final JsonNode jsonNode) {
        for (final var type : values()) {
            if (jsonNode.isTextual() && jsonNode.asText().equalsIgnoreCase(type.getResponseType())) {
                return type;
            }
            if (jsonNode.isNumber() && jsonNode.asInt() == type.ordinal()) {
                return type;
            }
            if (jsonNode.isObject() && jsonNode.has("type")) {
                return fromJsonNode(jsonNode.get("type"));
            }
        }
        throw new RuntimeException(String.format("Can't determine a query response type from '%s' ", jsonNode.toPrettyString()));
    }
}
