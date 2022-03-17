package com.sdk.accumulate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AccumulateApplication {

    public static void main(String[] args) {
//		URL.setURLStreamHandlerFactory(new CustomURLStreamHandlerFactory());
        SpringApplication.run(AccumulateApplication.class, args);
    }

}
