package com.sdk.accumulate.model;


import java.math.BigInteger;

public class AddCreditsArg {

    private String recipient;

    private BigInteger amount;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }
}
