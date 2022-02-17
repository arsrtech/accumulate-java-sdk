package com.sdk.accumulate.controller;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CustomURLConnection extends URLConnection {

    protected CustomURLConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        // Do your job here. As of now it merely prints "Connected!".
        System.out.println("Connected!");
    }

}