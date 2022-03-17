package com.sdk.accumulate.model;


public class AddCreditsArg {

    /**
     * Recipient who is going to receive the credits
     */
    private String recipient;

    /**
     * Credit Amount
     */
    private long amount;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
