package com.weather.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan
public class Application {

	public static void main(String[] args) {

		System.out.println("Hello, World!");
		SpringApplication.run(Application.class, args);
	}

}
