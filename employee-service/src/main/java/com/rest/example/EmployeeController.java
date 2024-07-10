package com.rest.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private static final Logger logger =
        LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> getAll()
    {
        return repository.findAll();
    }

    @GetMapping("/employees/{id}")
    Employee getById(@PathVariable Long id)
    {
        return repository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/employees/{id}")
    void deleteById(@PathVariable Long id)
    {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        repository.delete(employee);
        logger.info("{} deleted", employee);
        repository.flush();
    }

    @PutMapping("/employees/{id}")
    Employee replace(@RequestBody Employee newEmployee, @PathVariable Long id)
    {
        return repository.findById(id)
            .map(employee -> {
                logger.info("Updating {}", employee);
                if (newEmployee.getName() != null) {
                    employee.setName(newEmployee.getName());
                }
                if (newEmployee.getRole() != null) {
                    employee.setRole(newEmployee.getRole());
                }
                return repository.saveAndFlush(employee);
            })
            .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping("/employees")
    Employee insert(@RequestBody Employee newEmployee)
    {
        if (newEmployee.getId() != null) {
            repository.findById(newEmployee.getId()).map(employee -> {
                logger.info("Replacing {}", employee);
                return null;
            });
        }
        return repository.saveAndFlush(newEmployee);
    }

}
