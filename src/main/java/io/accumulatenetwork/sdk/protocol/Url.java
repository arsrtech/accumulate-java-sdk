package io.accumulatenetwork.sdk.protocol;

import com.fasterxml.jackson.annotation.JsonValue;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.split;

public class Url {

    private final URI url;


    @JsonValue
    public URI getUrl() {
        return url;
    }

    public Url(String value) throws MalformedURLException, URISyntaxException {
        final var url = new URI(value);
        if (url.getHost().isEmpty()) {
            throw new Error("Missing authority");
        }
        this.url = url;
    }

    public Url(URI url) {
        if (url.getHost() == null || url.getHost().isEmpty()) {
            throw new Error("Missing authority");
        }
        this.url = url;
    }

    public static Url toAccURL(String arg) {
        return Url.parse(arg);
    }

    /**
     * Parse a string into an AccURL
     */
    public static Url parse(String str) {
        try {
            if(!str.contains("://")) {
                str = "acc://" + str;
            }
            URI uri = new URI(str);
            return new Url(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String authority() {
        return this.url.getHost();
    }

    public String hostName() {
        if (isNotEmpty(url.getHost())) {
            final String[] split = split(url.getHost(), ':');
            return split[0];
        }
        return null;
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

    public Url rootUrl() {
        return parse(url.getScheme() + "://" + url.getHost());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Url url1 = (Url) o;
        return Objects.equals(url, url1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
