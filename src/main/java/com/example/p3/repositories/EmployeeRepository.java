package com.example.p3.repositories;

import com.example.p3.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Finds employees by initials (case-sensitive)
    List<Employee> findByInitials(String initials);

    // Finds employees by name (case-insensitive, partial match)
    List<Employee> findByNameContainingIgnoreCase(String name);
}
