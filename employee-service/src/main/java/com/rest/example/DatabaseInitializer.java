package com.rest.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    private static final Logger logger =
        LoggerFactory.getLogger(DatabaseInitializer.class);

    @Bean
    CommandLineRunner employeePopulator(EmployeeRepository repository)
    {
        return args -> {
            logger.info("Creating {}", repository.save(new Employee("Cornélio Sousa", "Back-end Developer")));
            logger.info("Creating {}", repository.save(new Employee("John Doe", "Scrum Master")));
            logger.info("Creating {}", repository.save(new Employee("John Hancock", "Front-end Developer")));
            repository.flush();
        };
    }

}
