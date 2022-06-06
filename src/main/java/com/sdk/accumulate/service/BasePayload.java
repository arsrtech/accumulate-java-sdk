package com.sdk.accumulate.service;

abstract class BasePayload implements Payload{

    private byte[] _binary;

    private byte[] _hash;

    @Override
    public byte[] marshalBinary() {
       if (this._binary != null) {
           return this._binary;
       }
       this._binary = this._marshalBinary();
       return this._binary;
    }

    @Override
    public byte[] hash() {
        if(this._hash != null) {
            return this._hash;
        }
        try {
            this._hash = Crypto.sha256(this._marshalBinary());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this._hash;
    }

    public abstract byte[] _marshalBinary();
}
