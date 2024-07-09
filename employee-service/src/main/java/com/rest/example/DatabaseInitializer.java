package com.rest.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner employeePopulator(EmployeeRepository repository)
    {
        return args -> {
            repository.save(new Employee("Cornélio Sousa", "Back-end Developer"));
            repository.save(new Employee("John Doe", "Scrum Master"));
            repository.save(new Employee("John Hancock", "Front-end Developer"));
            repository.flush();
        };
    }

}
