package com.example.p3.dtos.employee;

import com.example.p3.entities.Employee;
import lombok.Data;


@Data
public class EmployeeDto {
    // I use _ here instead of camelCase because that is whats inside the DB diagram.
    // This could be changed
    private String employee_id;
    private String name;
    private String initials;
    private String department_name;
    private Boolean is_Admin;

    public EmployeeDto(Employee employee) {
        this.employee_id = employee.getInitials();
        this.name = employee.getName();
        this.initials = employee.getInitials();
        this.department_name = employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null;
    }
}
