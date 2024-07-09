package com.rest.example;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String role;

    public Employee() { }

    public Employee(String name, String role)
    {
        this.name = name;
        this.role = role;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRole()
    {
        return this.role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) o;
        return Objects.equals(this.id, other.id)
                    && Objects.equals(this.name, other.name)
                    && Objects.equals(this.role, other.role);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString()
    {
        return String.format(
            "Employee{id=%d, name='%s', role='%s'}",
            this.id, this.name, this.role
        );
    }

}
