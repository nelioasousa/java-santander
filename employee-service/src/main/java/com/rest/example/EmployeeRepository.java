package com.rest.example;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>From {@link https://spring.io/guides/tutorials/rest}:
 * 
 * "Spring Data synthesizes implementations based on conventions found in the
 * naming of the methods in the interface."</p>
 * 
 * Spring Boot autoconfigures the database based on the H2 dependency in the
 * pom.xml?
 * 
 * <p>From {@link https://spring.io/guides/tutorials/rest}:
 * 
 * "Spring Data’s repository solution makes it possible to sidestep data store
 * specifics and, instead, solve a majority of problems by using domain-specific
 * terminology."</p>
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
