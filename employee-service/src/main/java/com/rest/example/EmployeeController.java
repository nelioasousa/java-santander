package com.rest.example;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler)
    {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> getAll()
    {
        List<EntityModel<Employee>> employess = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(employess,
            linkTo(methodOn(EmployeeController.class).getAll()).withSelfRel());
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> getOne(@PathVariable Long id)
    {
        return assembler.toModel(
            repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id))
        );
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id)
    {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        repository.delete(employee);
        logger.info("{} deleted", employee);
        repository.flush();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/employees/{id}")
    EntityModel<Employee> update(@RequestBody Employee newEmployee, @PathVariable Long id)
    {
        Employee employee;
        try {
            employee = repository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            throw new EmployeeNotFoundException(id);
        }
        if (newEmployee.getName() != null) {
            employee.setName(newEmployee.getName());
        }
        if (newEmployee.getRole() != null) {
            employee.setRole(newEmployee.getRole());
        }
        return assembler.toModel(repository.saveAndFlush(employee));
    }

    @PostMapping("/employees/{id}")
    EntityModel<Employee> replace(@RequestBody Employee newEmployee, @PathVariable Long id)
    {
        Optional<Employee> replacedEmployee = repository.findById(id);
        if (!replacedEmployee.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }
        newEmployee.setId(id);
        EntityModel<Employee> entityModel =
            assembler.toModel(repository.saveAndFlush(newEmployee));
        logger.info("{} replaced by {}", replacedEmployee.get(), entityModel.getContent());
        return entityModel;
    }

    @PostMapping("/employees")
    ResponseEntity<EntityModel<Employee>> insert(@RequestBody Employee newEmployee)
    {
        Optional<Employee> replacedEmployee =
            newEmployee.getId() == null ? Optional.empty() : repository.findById(newEmployee.getId());
        EntityModel<Employee> entityModel =
            assembler.toModel(repository.saveAndFlush(newEmployee));
        if (replacedEmployee.isPresent()) {
            logger.info("{} replaced by {}", replacedEmployee.get(), entityModel.getContent());
            return ResponseEntity
                .ok()
                .location(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
        } else {
            logger.info("{} created", entityModel.getContent());
            return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
        }
    }

}
