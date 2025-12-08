package com.example.p3.service;

import com.example.p3.entities.Employee;
import com.example.p3.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

        // Get employees by initials
    public Optional<Employee> getEmployeeByInitials(String initials) {
        // Calls the employee repository findByInitials
        return employeeRepository.findByInitials(initials);
    }
}