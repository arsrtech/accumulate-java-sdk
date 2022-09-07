package io.accumulatenetwork.sdk.protocol;

public class UrlRegistry {

    public Url getAcmeTokenUrl() {
        return StaticUrl.ACME_TOKEN_URL.getValue();
    }

    public boolean isUrlPattern(final String url, final UrlPattern pattern) {
        if (url == null) {
            throw new IllegalArgumentException("url may not be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern may not be null");
        }

        switch (pattern) {
            case LITE_ADDRESS:
                return LiteAddress.parse(url).getByteValue() != null;
            case LITE_TOKEN_ADDRESS:
                return LiteTokenAddress.parse(url).getByteValue() != null;
        }
        return pattern.getPattern().matcher(url).find();
    }
}
