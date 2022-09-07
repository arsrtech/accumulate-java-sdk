package io.accumulatenetwork.sdk.protocol;

import java.util.regex.Pattern;

public enum UrlPattern {

    LITE_ADDRESS(null),
    LITE_TOKEN_ADDRESS(null),
    DN_URL(Pattern.compile("^(acc://dnn)")),
    BVN_URL(Pattern.compile("^(acc://bvnn)"));

    private Pattern pattern;

    UrlPattern(final Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
