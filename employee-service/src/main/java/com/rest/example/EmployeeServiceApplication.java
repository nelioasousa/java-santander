package com.rest.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeServiceApplication {

	private static final Logger logger =
		LoggerFactory.getLogger(EmployeeServiceApplication.class);

	public static void main(String[] args) {
		logger.info("Application starting up..."); // Silly log
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}
