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

public class SyncRPCClient extends RPCClient {

    public SyncRPCClient() {
    }

    public SyncRPCClient(final URI uri) {
        super(uri);
    }


    public TxResponse sendTx(final TransactionBody body) {
        final RPCMethod rpcMethod = RPCMethod.fromClass(body.getClass());
        final RPCResponse rpcResponse = send(rpcMethod, body);
        final var txResponse = rpcResponse.asTransactionResponse();
        ResultReader.checkForErrors(txResponse);
        return txResponse;
    }

    public TransactionStatus sendTx(final EnvelopeBuilder envelopeBuilder) {
        final RPCResponse rpcResponse = sendInternal(RPCMethod.ExecuteDirect, envelopeBuilder);
        final var txResponse = rpcResponse.asTransactionResponse();
        if (txResponse.getResult() == null) {
            ResultReader.checkForErrors(txResponse);
        }
        final TransactionStatus transactionStatus = ResultReader.readValue(txResponse.getResult(), TransactionStatus.class);
        ResultReader.checkForErrors(txResponse, transactionStatus);
        return transactionStatus;
    }

    // Some GET operations don't have a body, just a method

    public RPCResponse send(final Marhallable payload) {
        if (payload == null) {
            throw new IllegalArgumentException("payload must not be empty");
        }
        final RPCMethod rpcMethod = RPCMethod.fromClass(payload.getClass());
        return sendInternal(rpcMethod, payload);
    }

    public RPCResponse send(final RPCMethod rpcMethod, final Marhallable payload) {
        if (rpcMethod == null) {
            throw new IllegalArgumentException("rpcMethod must not be empty");
        }
        return sendInternal(rpcMethod, payload);
    }

    private RPCResponse sendInternal(final RPCMethod rpcMethod, final Object body) {
        try {
            final HttpRequest request = buildRequest(rpcMethod, body);
            final var response = client.send(request, responseBodyHandler);
            if (response.statusCode() < 200 || response.statusCode() > 202) {
                throw buildResponseException(response);
            }
            return RPCResponse.from(response.body());
        } catch (final RPCException e) {
            throw e;
        } catch (final Exception e) {
            throw buildRequestException(e);
        }
    }
}
