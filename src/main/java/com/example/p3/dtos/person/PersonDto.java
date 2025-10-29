package com.example.p3.dtos.person;

import com.example.p3.model.person.Person;
import lombok.Data;
import com.example.p3.model.person.Role;

import java.util.ArrayList;

@Data
public class PersonDto {
    private long id;
    private String initials;
    private String name;
    private String email;
    private Person.Department department;
    private ArrayList<Role> roles;

    public PersonDto(Person p){
        this.id = p.getId();
        this.initials = p.getInitials();
        this.name = p.getName();
        this.email = p.getEmail();
        this.department = p.getDepartment();
        this.roles = p.getRoles();

    }
}
