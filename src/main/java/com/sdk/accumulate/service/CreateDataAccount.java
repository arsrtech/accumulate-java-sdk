package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.CreateDataAccountArg;

import java.math.BigInteger;

public class CreateDataAccount extends BasePayload{

    private final String url;

    private final String keyBookUrl;

    private final String managerKeyBookUrl;

    private final boolean scratch;

    public CreateDataAccount(CreateDataAccountArg createDataAccountArg) {
        super();
        this.url = createDataAccountArg.getUrl();
        this.keyBookUrl = createDataAccountArg.getKeyBookUrl();
        this.managerKeyBookUrl = createDataAccountArg.getManagerKeyBookUrl();
        this.scratch = createDataAccountArg.isScratch();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateDataAccount.getValue())));
        byte[] urlBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.url));
        byte[] keyBookUrlBytes = Crypto.append(Sequence.THREE,Marshaller.stringMarshaller(this.keyBookUrl));
        byte[] managerKeyBookUrlBytes = Crypto.append(Sequence.FOUR,Marshaller.stringMarshaller(this.managerKeyBookUrl));
        byte[] scratchBytes = Crypto.append(Sequence.FIVE,Marshaller.booleanMarshaller(this.scratch));
        return Crypto.append(typeBytes,urlBytes,keyBookUrlBytes,managerKeyBookUrlBytes,scratchBytes);
    }
}
