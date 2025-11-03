package com.example.p3.dtos.employee;

import com.example.p3.entities.Employee;
import com.example.p3.entities.Department;
import lombok.Data;


@Data
public class EmployeeDto {
    // I use _ here instead of camelCase because that is whats inside the DB diagram.
    // This could be changed
    private Integer employee_id;
    private String name;
    private String initial;
    private String department_name;
    private Boolean is_Admin;

    public EmployeeDto(Employee employee) {
        this.employee_id = employee.getId();
        this.name = employee.getName();
        this.initial = employee.getInitials();
        this.department_name = employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null;
        this.is_Admin = employee.getIsAdmin();
    }
}
