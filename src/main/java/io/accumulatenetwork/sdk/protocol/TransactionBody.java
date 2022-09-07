package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.accumulatenetwork.sdk.generated.protocol.AcmeFaucet;
import io.accumulatenetwork.sdk.generated.protocol.AddCredits;
import io.accumulatenetwork.sdk.generated.protocol.BurnTokens;
import io.accumulatenetwork.sdk.generated.protocol.CreateDataAccount;
import io.accumulatenetwork.sdk.generated.protocol.CreateIdentity;
import io.accumulatenetwork.sdk.generated.protocol.CreateKeyBook;
import io.accumulatenetwork.sdk.generated.protocol.CreateKeyPage;
import io.accumulatenetwork.sdk.generated.protocol.CreateToken;
import io.accumulatenetwork.sdk.generated.protocol.CreateTokenAccount;
import io.accumulatenetwork.sdk.generated.protocol.IssueTokens;
import io.accumulatenetwork.sdk.generated.protocol.RemoteTransaction;
import io.accumulatenetwork.sdk.generated.protocol.SendTokens;
import io.accumulatenetwork.sdk.generated.protocol.SyntheticBurnTokens;
import io.accumulatenetwork.sdk.generated.protocol.SyntheticCreateIdentity;
import io.accumulatenetwork.sdk.generated.protocol.SyntheticDepositCredits;
import io.accumulatenetwork.sdk.generated.protocol.SyntheticDepositTokens;
import io.accumulatenetwork.sdk.generated.protocol.SyntheticForwardTransaction;
import io.accumulatenetwork.sdk.generated.protocol.SyntheticWriteData;
import io.accumulatenetwork.sdk.generated.protocol.UpdateAccountAuth;
import io.accumulatenetwork.sdk.generated.protocol.UpdateKey;
import io.accumulatenetwork.sdk.generated.protocol.UpdateKeyPage;
import io.accumulatenetwork.sdk.generated.protocol.WriteData;
import io.accumulatenetwork.sdk.generated.protocol.WriteDataTo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateIdentity.class, name = "createIdentity"),
        @JsonSubTypes.Type(value = CreateTokenAccount.class, name = "createTokenAccount"),
        @JsonSubTypes.Type(value = SendTokens.class, name = "sendTokens"),
        @JsonSubTypes.Type(value = CreateDataAccount.class, name = "createDataAccount"),
        @JsonSubTypes.Type(value = WriteData.class, name = "writeData"),
        @JsonSubTypes.Type(value = WriteDataTo.class, name = "writeDataTo"),
        @JsonSubTypes.Type(value = AcmeFaucet.class, name = "acmeFaucet"),
        @JsonSubTypes.Type(value = CreateToken.class, name = "createToken"),
        @JsonSubTypes.Type(value = IssueTokens.class, name = "issueTokens"),
        @JsonSubTypes.Type(value = BurnTokens.class, name = "burnTokens"),
        @JsonSubTypes.Type(value = CreateKeyPage.class, name = "createKeyPage"),
        @JsonSubTypes.Type(value = CreateKeyBook.class, name = "createKeyBook"),
        @JsonSubTypes.Type(value = AddCredits.class, name = "addCredits"),
        @JsonSubTypes.Type(value = UpdateKeyPage.class, name = "updateKeyPage"),
        @JsonSubTypes.Type(value = UpdateAccountAuth.class, name = "updateAccountAuth"),
        @JsonSubTypes.Type(value = UpdateKey.class, name = "updateKey"),
        @JsonSubTypes.Type(value = RemoteTransaction.class, name = "remote"),
        @JsonSubTypes.Type(value = SyntheticCreateIdentity.class, name = "syntheticCreateIdentity"),
        @JsonSubTypes.Type(value = SyntheticWriteData.class, name = "syntheticWriteData"),
        @JsonSubTypes.Type(value = SyntheticDepositTokens.class, name = "syntheticDepositTokens"),
        @JsonSubTypes.Type(value = SyntheticDepositCredits.class, name = "syntheticDepositCredits"),
        @JsonSubTypes.Type(value = SyntheticBurnTokens.class, name = "syntheticBurnTokens"),
        @JsonSubTypes.Type(value = SyntheticForwardTransaction.class, name = "syntheticForwardTransaction"),
})
public interface TransactionBody extends Marhallable {
}
