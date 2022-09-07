package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.accumulatenetwork.sdk.generated.protocol.ADI;
import io.accumulatenetwork.sdk.generated.protocol.DataAccount;
import io.accumulatenetwork.sdk.generated.protocol.KeyBook;
import io.accumulatenetwork.sdk.generated.protocol.KeyPage;
import io.accumulatenetwork.sdk.generated.protocol.LiteDataAccount;
import io.accumulatenetwork.sdk.generated.protocol.LiteIdentity;
import io.accumulatenetwork.sdk.generated.protocol.LiteTokenAccount;
import io.accumulatenetwork.sdk.generated.protocol.TokenAccount;
import io.accumulatenetwork.sdk.generated.protocol.TokenIssuer;
import io.accumulatenetwork.sdk.generated.protocol.UnknownAccount;
import io.accumulatenetwork.sdk.generated.protocol.UnknownSigner;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UnknownAccount.class, name = "unknown"),
        @JsonSubTypes.Type(value = ADI.class, name = "identity"),
        @JsonSubTypes.Type(value = TokenIssuer.class, name = "tokenIssuer"),
        @JsonSubTypes.Type(value = TokenAccount.class, name = "tokenAccount"),
        @JsonSubTypes.Type(value = LiteTokenAccount.class, name = "liteTokenAccount"),
        @JsonSubTypes.Type(value = KeyPage.class, name = "keyPage"),
        @JsonSubTypes.Type(value = KeyBook.class, name = "keyBook"),
        @JsonSubTypes.Type(value = DataAccount.class, name = "dataAccount"),
        @JsonSubTypes.Type(value = LiteDataAccount.class, name = "liteDataAccount"),
        @JsonSubTypes.Type(value = UnknownSigner.class, name = "unknownSigner"),
        @JsonSubTypes.Type(value = LiteIdentity.class, name = "liteIdentity"),
})
public interface Account {
    Url getUrl();
}

