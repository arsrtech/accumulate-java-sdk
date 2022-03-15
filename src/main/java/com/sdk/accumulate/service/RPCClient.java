package com.sdk.accumulate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sdk.accumulate.model.RPCRequest;
import com.sdk.accumulate.model.TxnRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.util.Date;

public class RPCClient {

    private static final Logger logger = LoggerFactory.getLogger(RPCClient.class);

    private static final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public static String client(String url,TxnRequest object, String method) throws JsonProcessingException {

        RPCRequest rpcRequest = new RPCRequest("2.0",new Date().getTime(), method,object);

        String requestJson = ow.writeValueAsString(rpcRequest);
        logger.info("Request: {}",requestJson);

        try {

            SSLContext sslContext;
            logger.info("Loading HttpClient without two way SSL");
            sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, TrustAllStrategy.INSTANCE).build();

            HttpClient httpClient = HttpClients
                    .custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
            HttpPost post = new HttpPost(url);
            HttpEntity httpEntityNew = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
            post.setEntity(httpEntityNew);

            HttpResponse response = httpClient.execute(post);
            String responseJson = EntityUtils.toString(response.getEntity());
            post.abort();
            logger.info("Response: {}",responseJson);
            return responseJson;
        } catch (Exception e) {
            logger.error("HttpClient Error: ",e);
        }
        return null;
    }
}
