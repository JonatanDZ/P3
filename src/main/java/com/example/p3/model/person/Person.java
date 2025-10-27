package com.example.p3.model.person;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {
    private long id;
    private String initials;
    private String name;
    private String email;
    private Role role;
    private Department department;
    public enum Department {
        DEVOPS, FRONTEND, GAMES, PAYMENTS, PLAYERS, PROMOTIONS
    }

}