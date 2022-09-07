package io.accumulatenetwork.sdk.support;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.accumulatenetwork.sdk.support.HashUtils.sha256;


public class MerkleRootBuilder {

    private long count;
    private List<byte[]> pending = new ArrayList<>();
    private List<byte[]> hashList = new ArrayList<>();


    public byte[] addToMerkleTree(final byte[] sourceHash) {
        byte[] hash = Arrays.copyOf(sourceHash, sourceHash.length);
        hashList.add(hash);
        count++;
        padPending();
        for (int i = 0; i < pending.size(); i++) {
            byte[] v = pending.get(i);
            if (v == null) {
                pending.set(i, hash);
                return hash;
            }
            hash = sha256(ArrayUtils.addAll(v, hash));
            pending.set(i, null);
        }
        return hash;
    }

    private void padPending() {
        if (pending.isEmpty() || pending.get(pending.size() - 1) != null) {
            pending.add(null);
        }
    }

    public byte[] getMDRoot() {
        byte[] mdRoot = new byte[0];
        if (count == 0) {
            return mdRoot;
        }

        for (byte[] pendingHash : pending) {
            if (pendingHash == null) continue;

            if (mdRoot.length == 0) {
                mdRoot = Arrays.copyOf(pendingHash, pendingHash.length);
            } else {
                mdRoot = sha256(ArrayUtils.addAll(pendingHash, mdRoot));
            }
        }
        return mdRoot;
    }
}
