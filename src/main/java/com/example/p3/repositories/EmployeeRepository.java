package com.example.p3.repositories;

import com.example.p3.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Queries methods such as findAll() is standard in JpaRepository.
    // findByIntitials is a custome query to find initials, sql would be smt like:
    // SELECT * FROM employees WHERE initials = ?;
    // Finds employees by initials (case-sensitive)
    List<Employee> findByInitials(String initials);

    // Finds employees by name (case-insensitive, partial match)
    List<Employee> findByNameContainingIgnoreCase(String name);
}
