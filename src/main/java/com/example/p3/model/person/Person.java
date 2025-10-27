package com.example.p3.model.person;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Person {
    private long id;
    private String initials;
    private String name;
    private String email;
    private ArrayList<Role> roles;
    private Department department;
    public enum Department {
        DEVOPS, FRONTEND, GAMES, PAYMENTS, PLAYERS, PROMOTIONS
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}