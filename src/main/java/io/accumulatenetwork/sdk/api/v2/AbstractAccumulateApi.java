package io.accumulatenetwork.sdk.api.v2;

import io.accumulatenetwork.sdk.protocol.FactomEntry;
import io.accumulatenetwork.sdk.protocol.FactomExtRef;
import io.accumulatenetwork.sdk.support.HashBuilder;

import java.util.List;

public abstract class AbstractAccumulateApi {

    protected byte[][] factomExtRefsToByteArray(final List<FactomExtRef> extRefs) {
        int maxLen = 0;
        for (FactomExtRef extRef : extRefs) {
            if (extRef.getData() != null && extRef.getData().length > maxLen) {
                maxLen = extRef.getData().length;
            }
        }
        final byte[][] result = new byte[extRefs.size()][maxLen];
        for (int i = 0; i < extRefs.size(); i++) {
            final FactomExtRef extRef = extRefs.get(i);
            if (extRef != null) {
                result[i] = extRef.getData();
            }
        }
        return result;
    }

    protected static byte[] calculateFactomAccountId(final FactomEntry factomEntry) {
        final var hashBuilder = new HashBuilder();
        if (factomEntry.getData() != null) {
            hashBuilder.addBytes(factomEntry.getData());
        }
        factomEntry.getExtRefs().forEach(extRef -> {
            hashBuilder.addBytes(extRef.getData());
        });
        final byte[] accountId = new byte[32];
        System.arraycopy(hashBuilder.getCheckSum(), 0, accountId, 0, 32);
        return accountId;
    }
}
