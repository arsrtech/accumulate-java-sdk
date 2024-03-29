package com.sdk.accumulate.service;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Marshaller {

    public static byte[] uvarintMarshalBinary(BigInteger number) {
        byte[] bytesArray = new byte[0];
        while (number.compareTo(BigInteger.valueOf(0x80)) >= 0) {
            BigInteger clb =  clearBit(number,8);
            BigInteger clearBit = clb.or(BigInteger.valueOf(0x80));
//            logger.info("Bits: {} :  {}",number,Long.toBinaryString(clb.longValue()));
//            logger.info("Clear Bit num: {}",clearBit(number,8));
//            logger.info("First 8 Bits Hex : {}",Crypto.toHexString(toByteArray(clearBit(number,8).toByteArray())));
//            logger.info("Number {} :",number);
//            logger.info("Byte Array Before {}",Crypto.toHexString(bytesArray));
//            logger.info("First 8 Bits with Bitwise OR Operation Hex : {}",Crypto.toHexString(toByteArray(clearBit.toByteArray())));
            bytesArray = Crypto.append(bytesArray,toByteArray(clearBit.toByteArray()));
//            logger.info("Byte Array After {}",Crypto.toHexString(bytesArray));
            number = number.shiftRight(7);
        }
        bytesArray = Crypto.append(bytesArray,toByteArray(clearBit(number,8).toByteArray()));
        return bytesArray;
    }

    public static byte[] stringMarshaller(String data) {
        if (data == null) {
            return new byte[0];
        }
        return bytesMarshaller(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] booleanMarshaller(boolean data) {
        return new byte[]{(byte) (data?1:0)};
    }

    public static byte[] integerMarshaller(int data) {
        return uvarintMarshalBinary(BigInteger.valueOf(data));
    }

    public static byte[] longMarshaller(long data) {
        return uvarintMarshalBinary(BigInteger.valueOf(data));
    }

    public static byte[] bytesMarshaller(byte[] data) {
        byte[] len = uvarintMarshalBinary(BigInteger.valueOf(data.length));
        return Crypto.append(len,data);
    }

    public static BigInteger clearBit(BigInteger n,int len) {
        BigInteger tes = n;
        for (int i=len; i< n.bitLength(); i++) {
//            logger.info(Integer.toBinaryString(tes.intValue()));
            tes = tes.clearBit(i);
        }
        return tes;
    }

    public static byte[] toByteArray(byte[] value) {
        byte[] bytes = new byte[1];
        if (value.length > 1) {
            bytes[0] = value[1];
        } else {
            bytes[0] = value[0];
        }
        return bytes;
    }

    public static byte[] bigNumberMarshalBinary(BigInteger amount) {
        return bytesMarshaller(uvarintMarshalBinary(amount));
    }
}
