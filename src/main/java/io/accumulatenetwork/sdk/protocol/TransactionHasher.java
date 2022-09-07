package io.accumulatenetwork.sdk.protocol;


import io.accumulatenetwork.sdk.generated.protocol.AccumulateDataEntry;
import io.accumulatenetwork.sdk.generated.protocol.FactomDataEntry;
import io.accumulatenetwork.sdk.generated.protocol.Transaction;
import io.accumulatenetwork.sdk.generated.protocol.WriteData;
import io.accumulatenetwork.sdk.generated.protocol.WriteDataTo;
import io.accumulatenetwork.sdk.support.HashBuilder;

import java.nio.ByteBuffer;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;
import static io.accumulatenetwork.sdk.support.HashUtils.sha512;


public class TransactionHasher {

    public static Transaction hashTransaction(final Transaction transaction) {
        final var hashBuffer = ByteBuffer.allocate(64);

        final byte[] headerBinary = transaction.getHeader().marshalBinary();
        hashBuffer.put(sha256(headerBinary));

        final TransactionBody txBody = transaction.getBody();
        if (txBody instanceof WriteData) {
            hashWriteData(hashBuffer, (WriteData) txBody);
        } else if (txBody instanceof WriteDataTo) {
            hashWriteDataTo(hashBuffer, (WriteDataTo) txBody);
        } else {
            final byte[] payloadBinary = txBody.marshalBinary();
            hashBuffer.put(sha256(payloadBinary));
        }
        transaction.sethash(sha256(hashBuffer.array()));
        return transaction;
    }

    private static void hashWriteData(final ByteBuffer hashBuffer, final WriteData writeData) {
        final WriteData withoutEntry = new WriteData()
                .scratch(writeData.getScratch())
                .writeToState(writeData.getWriteToState());
        hashBuffer.put(hashWriteData(withoutEntry, writeData.getEntry()));
    }

    private static void hashWriteDataTo(final ByteBuffer hashBuffer, final WriteDataTo writeDataTo) {
        final WriteDataTo withoutEntry = new WriteDataTo()
                .recipient(writeDataTo.getRecipient());
        hashBuffer.put(hashWriteData(withoutEntry, writeDataTo.getEntry()));
    }

    private static byte[] hashWriteData(final TransactionBody withoutEntry, final DataEntry entry) {
        final var hashBuilder = new HashBuilder();
        hashBuilder.addBytes(withoutEntry.marshalBinary());
        if (entry == null) {
            hashBuilder.addBytes(new byte[32]);
        } else if (entry instanceof AccumulateDataEntry) {
            final var entryHashBuilder = new HashBuilder();
            byte[][] data = ((AccumulateDataEntry) entry).getData();
            for (int i = 0; i < data.length; i++) {
                entryHashBuilder.addBytes(data[i]);
            }
            final byte[] merkleHash = entryHashBuilder.merkleHash();
            hashBuilder.addHash(merkleHash);
        } else if (entry instanceof FactomDataEntry) {
            final byte[] data = entry.marshalBinary();
            byte[] sum = sha512(data);
            byte[] saltedSum = new byte[sum.length + data.length];
            System.arraycopy(sum, 0, saltedSum, 0, sum.length);
            System.arraycopy(data, 0, saltedSum, sum.length, data.length);
            return sha256(saltedSum);
        }
        return hashBuilder.merkleHash();
    }
}
