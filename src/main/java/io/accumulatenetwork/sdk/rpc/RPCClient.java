package io.accumulatenetwork.sdk.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.accumulatenetwork.sdk.generated.apiv2.RPCMethod;
import io.accumulatenetwork.sdk.rpc.models.RPCRequest;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

abstract class RPCClient {
    private static final Duration TIME_OUT = Duration.of(90, ChronoUnit.SECONDS); // TODO configurable

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    private static final Random random = new Random();

    private final URI uri;
    protected final HttpClient client = HttpClient.newHttpClient();
    protected final HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();


    RPCClient() {
        final String apiEndpoint = StringUtils.firstNonBlank(System.getProperty("accumulate.api"), System.getenv("ACC_API"));
        if (apiEndpoint == null) {
            throw new RuntimeException("The RPCClient() constructor needs either system property accumulate.api " +
                    "or environment variable ACC_API containing the Accumulate API endpoint");
        }
        try {
            this.uri = new URI(apiEndpoint);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    RPCClient(final URI uri) {
        this.uri = uri;
    }

    protected HttpRequest buildRequest(final RPCMethod rpcMethod, final Object body) throws JsonProcessingException {
        final JsonNode params = objectMapper.convertValue(body, JsonNode.class);
        final var rpcRequest = new RPCRequest("2.0", newRequestId(), rpcMethod.getApiMethod(), params);
        final String requestJson = objectWriter.writeValueAsString(rpcRequest);
        System.out.println("Request: " + requestJson); // TODO remove or convert to logger

        final var request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(TIME_OUT)
                .POST(HttpRequest.BodyPublishers.ofString(requestJson, StandardCharsets.UTF_8))
                .build();
        return request;
    }

    protected RuntimeException buildRequestException(final Exception e) {
        return new RuntimeException(String.format("Posting the request to Accumulate endpoint %s failed", uri), e);
    }

    protected RuntimeException buildResponseException(final HttpResponse<String> response) {
        return new RuntimeException(
                String.format("HTTP error response from Accumulate endpoint %s, status code: %d, message: %s",
                        uri, response.statusCode(), response.body()));
    }

    private Integer newRequestId() {
        return random.nextInt(5000); // TODO why 5000 in CLI?
    }
}
