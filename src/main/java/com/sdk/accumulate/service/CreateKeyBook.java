package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.Sequence;
import com.sdk.accumulate.enums.TxType;
import com.sdk.accumulate.model.CreateKeyBookArg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CreateKeyBook extends BasePayload {

    private final AccURL url;

    private final List<AccURL> pages;

    public CreateKeyBook(CreateKeyBookArg createKeyBookArg) throws Exception {
        super();
        this.url = AccURL.toAccURL(createKeyBookArg.getUrl());
        List<AccURL> pageList = new ArrayList<>();
        for (String accUrl: createKeyBookArg.getPages()) {
            pageList.add(AccURL.toAccURL(accUrl));
        }
        this.pages = pageList;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Crypto.append(Sequence.ONE,Marshaller.uvarintMarshalBinary(BigInteger.valueOf(TxType.CreateKeyBook.getValue())));
        byte[] urlBytes = Crypto.append(Sequence.TWO,Marshaller.stringMarshaller(this.url.string()));
        byte[] pageBytes = new byte[0];
        for (AccURL accURL: this.pages) {
            pageBytes = Crypto.append(pageBytes,Marshaller.stringMarshaller(accURL.string()));

        }
        pageBytes = Crypto.append(Sequence.THREE,pageBytes);
        return Crypto.append(typeBytes,urlBytes,pageBytes);

    }
}
