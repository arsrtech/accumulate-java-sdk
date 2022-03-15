package com.sdk.accumulate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;

public class AccURL {

    private static final Logger logger = LoggerFactory.getLogger(AccURL.class);

    static AccURL ACME_TOKEN_URL;

    static {
        try {
            ACME_TOKEN_URL = AccURL.parse("acc://ACME");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private final URI url;

    public static AccURL getAcmeTokenUrl() {
        return ACME_TOKEN_URL;
    }

    public static void setAcmeTokenUrl(AccURL acmeTokenUrl) {
        ACME_TOKEN_URL = acmeTokenUrl;
    }

    public URI getUrl() {
        return url;
    }

    public AccURL(URI url) throws MalformedURLException {
//        if (!url.getProtocol().equals("acc")) {
//            throw new Error("Invalid protocol: "+url.getProtocol());
//        }
        if (url.getHost().isEmpty()) {
            throw new Error("Missing authority");
        }
        this.url = url;
    }

    public static AccURL toAccURL(String arg ) throws Exception {
        return  AccURL.parse(arg);
    }

    /**
     * Parse a string into an AccURL
     */
    static AccURL parse(String str) throws Exception {
        URI uri = new URI(str);
        return new AccURL(uri);
    }

    public String authority() {
        return this.url.getHost();
    }

    public String path() {
        return this.url.getPath();
    }

    public String query() {
        return this.url.getQuery();
    }

    public String fragment() {
        return this.url.getAuthority();
    }

    public String string() {
        return this.url.toString();
    }
}
