package io.accumulatenetwork.sdk.tests;

import io.accumulatenetwork.sdk.generated.protocol.SignatureType;
import io.accumulatenetwork.sdk.protocol.LiteAccount;
import org.junit.jupiter.api.BeforeAll;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public abstract class AbstractTestBase {

    LiteAccount liteAccount;

    protected String rootADI = "acc://test" + new Random().nextInt() + ".acme"; // TODO make Url


    @BeforeAll
    void init() {
        this.liteAccount = LiteAccount.generate(SignatureType.ED25519);
    }

    protected void waitForAnchor() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    protected byte[][] buildDataRecord(String... entries) {
        int maxLen = 0;
        for (final String entry : entries) {
            if (entry != null && entry.length() > maxLen) {
                maxLen = entry.length();
            }
        }
        final byte[][] result = new byte[entries.length][maxLen];
        for (int i = 0; i < entries.length; i++) {
            result[i] = entries[i].getBytes(StandardCharsets.UTF_8);
        }
        return result;
    }
}
