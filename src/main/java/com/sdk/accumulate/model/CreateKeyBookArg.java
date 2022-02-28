package com.sdk.accumulate.model;

import java.util.List;

public class CreateKeyBookArg {

    private String url;

    private List<String> pages;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }
}
