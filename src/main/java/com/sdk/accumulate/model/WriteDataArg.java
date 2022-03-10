package com.sdk.accumulate.model;

import java.util.List;

public class WriteDataArg {

    private List<byte[]> extIds;

    private byte[] data;

    public List<byte[]> getExtIds() {
        return extIds;
    }

    public void setExtIds(List<byte[]> extIds) {
        this.extIds = extIds;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
