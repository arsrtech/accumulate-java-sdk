package io.accumulatenetwork.sdk.protocol;

import java.util.Locale;

public enum StaticUrl {
    ACME_TOKEN_URL(Url.parse("acc://ACME"));

    private Url value;

    StaticUrl(final Url value) {
        this.value = value;
    }

    public Url getValue() {
        return value;
    }

    public static StaticUrl matchPrefix(final String url) {
        if (url == null) {
            throw new IllegalArgumentException("url value may not be empty");
        }
        for (var item : StaticUrl.values()) {
            if (item.value.string().toLowerCase(Locale.ROOT).startsWith(url.toLowerCase(Locale.ROOT))) {
                return item;
            }
        }
        throw new IllegalArgumentException("can't match an UrlPattern for " + url);
    }
}
