package com.example.p3.dtos.employee;

import com.example.p3.model.employee.Employee;
import lombok.Data;
import com.example.p3.model.employee.Role;

import java.util.ArrayList;

@Data
public class EmployeeDto {
    private long id;
    private String initials;
    private String name;
    private String email;
    private Employee.Department department;
    private ArrayList<Role> roles;

    public EmployeeDto(Employee e){
        this.id = e.getId();
        this.initials = e.getInitials();
        this.name = e.getName();
        this.email = e.getEmail();
        this.department = e.getDepartment();
        this.roles = e.getRoles();
    }
}
