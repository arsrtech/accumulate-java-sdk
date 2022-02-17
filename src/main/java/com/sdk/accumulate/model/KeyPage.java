package com.sdk.accumulate.model;

public class KeyPage {

    private long height;

    private long index;

    public KeyPage() {
    }

    public KeyPage(long height, long index) {
        this.height = height;
        this.index = index;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }
}
