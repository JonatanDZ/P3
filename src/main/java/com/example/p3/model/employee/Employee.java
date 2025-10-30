package com.example.p3.model.employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Employee {
    private long id;
    private String initials;
    private String name;
    private String email;
    private ArrayList<Role> roles;
    private Department department;
    public enum Department {
        DEVOPS, FRONTEND, GAMES, PAYMENTS, PLAYERS, PROMOTIONS
    }
//    Måde at tilføje på
//    public void addRole(Role role) {
//        roles.add(role);
//    }
}


///metoder
//Add personal tool
//Search person + tool
//Personal tool edited
//Personal tool removed
//Get favorites