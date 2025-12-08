package com.example.p3.repositories;

import com.example.p3.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Queries methods such as findAll() is standard in JpaRepository.
    // findByIntitials is a custom query to find initials, sql would be:
    // SELECT * FROM employees WHERE initials = ?;
    // findByInitials is a costume query to find initials, sql would be smt like:
    Optional<Employee> findByInitials(String initials);
}