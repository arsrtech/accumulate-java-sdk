package com.sdk.accumulate.payload;

import java.math.BigInteger;

public class BurnTokensPayload {

    private String type;

    private String  amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
