package com.example.p3.service;

import com.example.p3.entities.Employee;
import com.example.p3.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        // Standard Jpa query method.
        return employeeRepository.findAll();
    }
    
    // Get employees by initials
    public Optional<Employee> getEmployeeByInitials(String initials) {
        return employeeRepository.findByInitials(initials);
    }

    // Get employees by name
    public Optional<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartment_Name(departmentName);
    }
}