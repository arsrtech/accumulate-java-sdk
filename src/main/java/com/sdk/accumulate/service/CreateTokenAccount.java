package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.CreateTokenAccountArg;

import java.math.BigInteger;

public class CreateTokenAccount extends AccountPayload {

    private final AccURL tokenUrl;

    private final AccURL keyBookUrl;

    private final boolean scratch;

    public CreateTokenAccount(CreateTokenAccountArg createTokenAccountArg) throws Exception {
        super(AccURL.toAccURL(createTokenAccountArg.getUrl()));
        this.tokenUrl = AccURL.toAccURL(createTokenAccountArg.getTokenUrl());
        this.keyBookUrl = AccURL.toAccURL(createTokenAccountArg.getKeyBookUrl());
        this.scratch = createTokenAccountArg.isScratch();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateTokenAccount.getValue())));
        byte[] urlBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(getUrl().string()));
        byte[] tokenUrlBytes = Crypto.append(Sequence.THREE,Marshaller.stringMarshaller(this.tokenUrl.string()));
        byte[] keyBookUrlBytes = Crypto.append(Sequence.FOUR,Marshaller.stringMarshaller(this.keyBookUrl.string()));
        byte[] scratchBytes = Crypto.append(Sequence.FIVE,Marshaller.booleanMarshaller(this.scratch));
        return Crypto.append(typeBytes,urlBytes,tokenUrlBytes,keyBookUrlBytes,scratchBytes);
    }
}
