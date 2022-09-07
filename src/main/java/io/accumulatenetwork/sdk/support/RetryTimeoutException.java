package io.accumulatenetwork.sdk.support;

public class RetryTimeoutException extends RuntimeException {
    public RetryTimeoutException(final String message) {
        super(message);
    }
}
