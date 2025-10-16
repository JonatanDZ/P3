package com.example.p3.model;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private long id;
    private String initials;
    private String name;
    private String email;
    private Department department;
    public enum Department {
        DEVOPS, FRONTEND, GAMES, PAYMENTS, PLAYERS, PROMOTIONS
    }
    private boolean admin;

    //Favorites tror ikke den skal være der, da der skal bruges et bridging table og derfor kommer favorites gennem initialer -> userId -> bridging table -> favorites
}
