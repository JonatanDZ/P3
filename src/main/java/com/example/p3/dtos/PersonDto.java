package com.example.p3.dtos;

import com.example.p3.model.Person;
import lombok.Data;

@Data
public class PersonDto {
    private long id;
    private String initials;
    private String name;
    private String email;
    private Person.Department department;
    private boolean admin;

    public PersonDto(Person u){
        this.id = u.getId();
        this.initials = u.getInitials();
        this.name = u.getName();
        this.email = u.getEmail();
        this.department = u.getDepartment();
        this.admin = u.isAdmin();
    }
}
