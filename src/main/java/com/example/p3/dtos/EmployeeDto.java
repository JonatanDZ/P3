package com.example.p3.dtos;

import com.example.p3.entities.Employee;

import lombok.Data;

@Data
public class EmployeeDto {
    // Attributes of en employee
    private String id;
    private String name;
    private String initials;
    private String department_name;
    private String email;

    // Constructor to create an employeeDto
    public EmployeeDto(Employee e) {
        this.id = e.getInitials();
        this.name = e.getName();
        this.initials = e.getInitials();
        this.department_name = e.getDepartment() != null ? e.getDepartment().getName() : null;
        this.email = e.getEmail();
    }
}
