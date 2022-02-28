package com.sdk.accumulate.service;

import com.sdk.accumulate.enums.TxnType;
import com.sdk.accumulate.model.CreateKeyBookArg;

import java.util.ArrayList;
import java.util.List;

public class CreateKeyBook extends BasePayload {

    private AccURL url;

    private List<AccURL> pages;

    public CreateKeyBook(CreateKeyBookArg createKeyBookArg) throws Exception {
        this.url = AccURL.toAccURL(createKeyBookArg.getUrl());
        List<AccURL> pageList = new ArrayList<>();
        for (String accUrl: createKeyBookArg.getPages()) {
            pageList.add(AccURL.toAccURL(accUrl));
        }
        this.pages = pageList;
    }

    public AccURL getUrl() {
        return url;
    }

    public void setUrl(AccURL url) {
        this.url = url;
    }

    public List<AccURL> getPages() {
        return pages;
    }

    public void setPages(List<AccURL> pages) {
        this.pages = pages;
    }

    @Override
    public byte[] _marshalBinary() {
        byte[] typeBytes = Marshaller.stringMarshaller(TxnType.CreateKeyBook.getValue());
        byte[] urlBytes = Marshaller.stringMarshaller(this.url.string());
        byte[] pageLenBytes = Marshaller.integerMarshaller(this.pages.toArray().length);
        byte[] pageBytes = new byte[0];
        for (AccURL accURL: this.pages) {
            pageBytes = Crypto.append(pageBytes,Marshaller.stringMarshaller(accURL.string()));
        }
        return Crypto.append(typeBytes,urlBytes,pageLenBytes,pageBytes);

    }
}
