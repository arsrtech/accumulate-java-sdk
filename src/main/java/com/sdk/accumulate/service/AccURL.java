package com.sdk.accumulate.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class AccURL {

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
        if (url.getHost().isEmpty()) {
            throw new Error("Missing authority");
        }
        this.url = url;
    }

    public static AccURL toAccURL(String arg) {
        return AccURL.parse(arg);
    }

    /**
     * Parse a string into an AccURL
     */
    static AccURL parse(String str) {
        try {
            URI uri = new URI(str);
            return new AccURL(uri);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
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

    public AccURL rootUrl() {
        return parse(url.getScheme() + "://" + url.getHost());
    }
}
