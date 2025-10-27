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

    public PersonDto(Person p){
        this.id = p.getId();
        this.initials = p.getInitials();
        this.name = p.getName();
        this.email = p.getEmail();
        this.department = p.getDepartment();
        this.admin = p.isAdmin();
    }
}
