package com.sdk.accumulate.controller;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class CustomURLStreamHandlerFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if ("acc".equals(protocol)) {
            return new CustomURLStreamHandler();
        }

        return null;
    }

}
