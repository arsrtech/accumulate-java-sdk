package io.accumulatenetwork.sdk.generated.errors;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("Error")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error implements Marhallable {
	private String message;
	private Status code;
	private Error cause;
	private CallSite[] callStack;

    //
	public String getMessage() {
	    return message;
	}
	public void setMessage(final String value) {
	    this.message = value;
	}

	public Error message(final String value) {
	    setMessage(value);
	    return this;
	}
	public Status getCode() {
	    return code;
	}
	public void setCode(final Status value) {
	    this.code = value;
	}

	public Error code(final Status value) {
	    setCode(value);
	    return this;
	}
	public Error getCause() {
	    return cause;
	}
	public void setCause(final Error value) {
	    this.cause = value;
	}

	public Error cause(final Error value) {
	    setCause(value);
	    return this;
	}
	public CallSite[] getCallStack() {
	    return callStack;
	}
	public void setCallStack(final CallSite[] value) {
	    this.callStack = value;
	}

	public Error callStack(final CallSite[] value) {
	    setCallStack(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.message == null || this.message.length() == 0)) {
            marshaller.writeString(1, this.message);
        }
        if (!(this.code == null)) {
            marshaller.writeValue(2, code);
        }
        if (!(this.cause == null)) {
            marshaller.writeValue(3, cause);
        }
        if (!(this.callStack == null)) {
            marshaller.writeValue(4, callStack);
        }
        return marshaller.array();
    }
}