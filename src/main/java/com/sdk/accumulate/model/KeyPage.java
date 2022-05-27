package com.sdk.accumulate.model;

public class KeyPage {

    private long height;

//    private long index;

    private long version;

    public KeyPage() {
    }

    public KeyPage(long height, long index) {
        this.height = height;
//        this.index = index;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    //    public long getIndex() {
//        return index;
//    }
//
//    public void setIndex(long index) {
//        this.index = index;
//    }
}
