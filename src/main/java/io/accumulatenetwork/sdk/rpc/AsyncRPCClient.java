package io.accumulatenetwork.sdk.rpc;

import io.accumulatenetwork.sdk.generated.apiv2.RPCMethod;
import io.accumulatenetwork.sdk.generated.apiv2.TxResponse;
import io.accumulatenetwork.sdk.generated.protocol.TransactionStatus;
import io.accumulatenetwork.sdk.protocol.EnvelopeBuilder;
import io.accumulatenetwork.sdk.protocol.Marhallable;
import io.accumulatenetwork.sdk.protocol.TransactionBody;
import io.accumulatenetwork.sdk.rpc.models.RPCResponse;
import io.accumulatenetwork.sdk.support.ResultReader;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;

public class AsyncRPCClient extends RPCClient {

    public AsyncRPCClient() {
    }

    public AsyncRPCClient(final URI uri) {
        super(uri);
    }


    public CompletableFuture<TxResponse> sendTx(final TransactionBody body) {
        final CompletableFuture<TxResponse> completableFuture = new CompletableFuture<>();
        final RPCMethod rpcMethod = RPCMethod.fromClass(body.getClass());
        send(rpcMethod, body).thenAccept(rpcResponse -> {
                    try {
                        final var txResponse = rpcResponse.asTransactionResponse();
                        ResultReader.checkForErrors(txResponse);
                        completableFuture.complete(txResponse);
                    } catch (Throwable throwable) {
                        completableFuture.completeExceptionally(throwable);
                    }
                })
                .exceptionally(throwable -> {
                    completableFuture.completeExceptionally(throwable);
                    return null;
                });
        return completableFuture;
    }

    public CompletableFuture<TransactionStatus> sendTx(final EnvelopeBuilder envelopeBuilder) {
        final CompletableFuture<TransactionStatus> completableFuture = new CompletableFuture<>();
        sendInternalAsync(RPCMethod.ExecuteDirect, envelopeBuilder)
                .thenAccept(rpcResponse -> {
                    try {
                        final var txResponse = rpcResponse.asTransactionResponse();
                        if (txResponse.getResult() == null) {
                            ResultReader.checkForErrors(txResponse);
                        }
                        final TransactionStatus transactionStatus = ResultReader.readValue(txResponse.getResult(), TransactionStatus.class);
                        ResultReader.checkForErrors(txResponse, transactionStatus);
                        completableFuture.complete(transactionStatus);
                    } catch (Throwable throwable) {
                        completableFuture.completeExceptionally(throwable);
                    }

                })
                .exceptionally(throwable -> {
                    completableFuture.completeExceptionally(throwable);
                    return null;
                });
        return completableFuture;
    }

    // Some GET operations don't have a body, just a method
    public CompletableFuture<RPCResponse> send(final Marhallable payload) {
        if (payload == null) {
            throw new IllegalArgumentException("payload must not be empty");
        }
        final RPCMethod rpcMethod = RPCMethod.fromClass(payload.getClass());
        return sendInternalAsync(rpcMethod, payload);
    }

    public CompletableFuture<RPCResponse> send(final RPCMethod rpcMethod, final Marhallable payload) {
        if (rpcMethod == null) {
            throw new IllegalArgumentException("rpcMethod must not be empty");
        }
        return sendInternalAsync(rpcMethod, payload);
    }

    private CompletableFuture<RPCResponse> sendInternalAsync(final RPCMethod rpcMethod, final Object body) {
        final CompletableFuture<RPCResponse> completableFuture = new CompletableFuture<>();
        try {
            final HttpRequest request = buildRequest(rpcMethod, body);
            client.sendAsync(request, responseBodyHandler).thenAccept(response -> {
                if (response.statusCode() < 200 || response.statusCode() > 202) {
                    completableFuture.completeExceptionally(buildResponseException(response));
                }
                try {
                    completableFuture.complete(RPCResponse.from(response.body()));
                } catch (final RPCException e) {
                    completableFuture.completeExceptionally(e);
                }
            });
        } catch (final Exception e) {
            throw buildRequestException(e);
        }
        return completableFuture;
    }
}
