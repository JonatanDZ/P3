package com.example.p3.dtos;

import com.example.p3.entities.Employee;
import lombok.Data;


@Data
public class EmployeeDto {
    // I use _ here instead of camelCase because that is whats inside the DB diagram.
    // This could be changed
    private String id;
    private String name;
    private String initials;
    private String department_name;

    public EmployeeDto(Employee e) {
        this.id = e.getInitials();
        this.name = e.getName();
        this.initials = e.getInitials();
        this.department_name = e.getDepartment() != null ? e.getDepartment().getName() : null;
    }
}
