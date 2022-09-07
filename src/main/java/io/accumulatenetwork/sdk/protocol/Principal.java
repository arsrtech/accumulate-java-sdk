package io.accumulatenetwork.sdk.protocol;

public class Principal {

    private Account account;
    private SignatureKeyPair signatureKeyPair;

    public Principal(final Account account, final SignatureKeyPair signatureKeyPair) {
        this.account = account;
        this.signatureKeyPair = signatureKeyPair;
    }

    public Account getAccount() {
        return account;
    }

    public SignatureKeyPair getSignatureKeyPair() {
        return signatureKeyPair;
    }
}
