package io.accumulatenetwork.sdk.rpc;

public class RPCException extends RuntimeException {

    private final int code;

    public RPCException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
