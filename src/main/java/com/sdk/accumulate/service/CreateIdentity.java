package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.CreateIdentityArg;
import java.math.BigInteger;

public class CreateIdentity extends AccountPayload {


    private final byte[] publicKey;

    private final String keyBookName;

    private final String keyPageName;

    public CreateIdentity(CreateIdentityArg createIdentityArg) {
        super(AccURL.toAccURL(createIdentityArg.getUrl()));
        this.publicKey = createIdentityArg.getPublicKey();
        this.keyBookName = createIdentityArg.getKeyBookName();
        this.keyPageName = createIdentityArg.getKeyPageName();
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE, Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateIdentity.getValue())));
        byte[] urlBytes = Crypto.append(Sequence.TWO, Marshaller.stringMarshaller(getUrl().string()));
        byte[] keyBytes = Crypto.append(Sequence.THREE, Marshaller.bytesMarshaller(this.publicKey));
        byte[] keyBookNameBytes = Crypto.append(Sequence.FOUR, Marshaller.stringMarshaller(this.keyBookName));
        byte[] keyBookPageBytes = Crypto.append(Sequence.FIVE, Marshaller.stringMarshaller(this.keyPageName));
        return Crypto.append(typeBytes, urlBytes, keyBytes, keyBookNameBytes, keyBookPageBytes);
    }
}
