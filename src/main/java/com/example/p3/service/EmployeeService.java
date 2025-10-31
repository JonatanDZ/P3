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
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeesByDepartment() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeesById() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeesByInitials() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeesByName() {
        return employeeRepository.findAll();
    }
}