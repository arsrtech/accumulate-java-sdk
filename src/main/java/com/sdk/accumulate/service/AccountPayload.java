package com.sdk.accumulate.service;

abstract class AccountPayload extends BasePayload {

    private final AccURL url;

    public AccountPayload(final AccURL url) {
        this.url = url;
    }


    public AccURL getUrl() {
        return url;
    }
}
