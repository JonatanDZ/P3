package com.example.p3.repositories;

import com.example.p3.entities.Employee;
import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Queries methods such as findAll() is standard in JpaRepository.
    // findByIntitials is a custom query to find initials, sql would be:
    // SELECT * FROM employees WHERE initials = ?;
    // findByInitials is a costume query to find initials, sql would be smt like:
    Optional<Employee> findByInitials(String initials);
}

// SELECT * FROM employees WHERE initials = ?;


// Starts from the Employee entity, joins the related Department table, and filters where Department.departmentName matches the given value.
// The "_" means JPA looks inside the Department entity to access the departmentName field.
//List<Employee> findByDepartment_DepartmentName(String departmentName);



// Finds employees by name (case-insensitive, partial match)
//Optional<Employee> findByNameContainingIgnoreCase(String name);
