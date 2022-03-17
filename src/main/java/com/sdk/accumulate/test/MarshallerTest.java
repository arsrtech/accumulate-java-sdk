package com.sdk.accumulate.test;

import com.sdk.accumulate.service.Marshaller;

import java.math.BigInteger;

public class MarshallerTest {

    public static void main(String[] args) {
        Marshaller.uvarintMarshalBinary(BigInteger.valueOf(4562793472L));
    }
}
