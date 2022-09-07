package io.accumulatenetwork.sdk.generated.query;

/**
    GENERATED BY go run ./tools/cmd/gen-api. DO NOT EDIT.
**/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.accumulatenetwork.sdk.protocol.Account;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.support.Marshaller;
// UnionType: 
// UnionValue: 

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ResponseAccount")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAccount implements Marhallable {
	private Account account;
	private ChainState[] chainState;
	private GeneralReceipt receipt;

    //
	public Account getAccount() {
	    return account;
	}
	public void setAccount(final Account value) {
	    this.account = value;
	}

	public ResponseAccount account(final Account value) {
	    setAccount(value);
	    return this;
	}
	public ChainState[] getChainState() {
	    return chainState;
	}
	public void setChainState(final ChainState[] value) {
	    this.chainState = value;
	}

	public ResponseAccount chainState(final ChainState[] value) {
	    setChainState(value);
	    return this;
	}
	public GeneralReceipt getReceipt() {
	    return receipt;
	}
	public void setReceipt(final GeneralReceipt value) {
	    this.receipt = value;
	}

	public ResponseAccount receipt(final GeneralReceipt value) {
	    setReceipt(value);
	    return this;
	}

    public byte[] marshalBinary() {
        final var marshaller = new Marshaller();
        if (!(this.account == null)) {
            marshaller.writeValue(1, account);
        }
        if (!(this.chainState == null)) {
            marshaller.writeValue(2, chainState);
        }
        if (!(this.receipt == null)) {
            marshaller.writeValue(3, receipt);
        }
        return marshaller.array();
    }
}