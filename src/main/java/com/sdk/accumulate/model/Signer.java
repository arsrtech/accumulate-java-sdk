package com.sdk.accumulate.model;

public class Signer {

    private String url;

    private String publicKey;

    private int version;

    private long timestamp;

    private String signatureType;

    private boolean useSimpleHash;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public boolean isUseSimpleHash() {
        return useSimpleHash;
    }

    public void setUseSimpleHash(boolean useSimpleHash) {
        this.useSimpleHash = useSimpleHash;
    }
}
