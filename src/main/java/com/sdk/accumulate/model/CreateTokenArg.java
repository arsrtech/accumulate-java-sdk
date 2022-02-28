package com.sdk.accumulate.model;

public class CreateTokenArg {

    private String url;

    private String keyBookUrl;

    private String symbol;

    private int precision;

    private String properties;

    private int initialSupply;

    private boolean hasSupplyLimit;

    private String manager;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyBookUrl() {
        return keyBookUrl;
    }

    public void setKeyBookUrl(String keyBookUrl) {
        this.keyBookUrl = keyBookUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public int getInitialSupply() {
        return initialSupply;
    }

    public void setInitialSupply(int initialSupply) {
        this.initialSupply = initialSupply;
    }

    public boolean isHasSupplyLimit() {
        return hasSupplyLimit;
    }

    public void setHasSupplyLimit(boolean hasSupplyLimit) {
        this.hasSupplyLimit = hasSupplyLimit;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
