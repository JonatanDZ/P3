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

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Integer employee_id) {
        return employeeRepository.findById(employee_id);
    }

    // Get employees by initials
    public List<Employee> getEmployeesByInitials(String initials) {
        return employeeRepository.findByInitials(initials);
    }

    // Get employees by name
    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        return employeeRepository.findByDepartment_DepartmentName(departmentName);
    }
}