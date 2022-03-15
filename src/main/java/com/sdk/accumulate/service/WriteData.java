package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.WriteDataArg;

import java.math.BigInteger;
import java.util.List;

public class WriteData extends BasePayload {

    private List<byte[]> extIds;

    private byte[] data;

    public WriteData(WriteDataArg writeDataArg) {
        super();
        this.extIds = writeDataArg.getExtIds();
        this.data = writeDataArg.getData();
    }

    public List<byte[]> getExtIds() {
        return extIds;
    }

    public void setExtIds(List<byte[]> extIds) {
        this.extIds = extIds;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.WriteData.getValue())));
        byte[] extIdBytes = new byte[0];
        for (byte[] bytes: this.extIds) {
            extIdBytes = Crypto.append(extIdBytes,Marshaller.bytesMarshaller(bytes));
        }
        byte[] data = Crypto.append(Sequence.TWO,Marshaller.bytesMarshaller(this.data));
        return Crypto.append(typeBytes,data);
    }
}
