package com.sdk.accumulate.service;

abstract class BasePayload implements Payload{

    private byte[] _binary;

    @Override
    public byte[] marshalBinary() {
       if (this._binary != null) {
           return this._binary;
       }
       this._binary = this._marshalBinary();
       return this._binary;
    }

    public abstract byte[] _marshalBinary();
}
