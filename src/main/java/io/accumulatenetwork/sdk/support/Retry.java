package io.accumulatenetwork.sdk.support;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.BooleanSupplier;

public class Retry {

    private Duration timeout;
    private Duration delay;
    private String message;

    public Retry withTimeout(final long timeout, final ChronoUnit unit) {
        this.timeout = Duration.of(timeout, unit);
        return this;
    }

    public Retry withDelay(final long delay, final ChronoUnit unit) {
        this.delay = Duration.of(delay, unit);
        return this;
    }

    public Retry withMessage(final String message) {
        this.message = message;
        return this;
    }

    public void execute(final BooleanSupplier callback) {
        if (timeout == null) {
            throw new IllegalArgumentException("timeout must not be null");
        }

        final Instant startTime = Instant.now();
        while (callback.getAsBoolean()) {
            if (Duration.between(startTime, Instant.now()).compareTo(timeout) > -0) {
                throw new RetryTimeoutException(String.format(message, timeout.toString().substring(2)));
            }
            delay();
        }
    }

    private void delay() {
        if (delay != null) {
            try {
                Thread.sleep(delay.toMillis());
            } catch (InterruptedException e) {
            }
        }
    }
}
