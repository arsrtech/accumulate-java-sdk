package com.sdk.accumulate;

import com.sdk.accumulate.controller.CustomURLStreamHandlerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;

@SpringBootApplication
public class AccumulateApplication {

	public static void main(String[] args) {
//		URL.setURLStreamHandlerFactory(new CustomURLStreamHandlerFactory());
		SpringApplication.run(AccumulateApplication.class, args);
	}

}
