package com.example.p3.model;
import lombok.Getter;
import lombok.Setter;

// adding getters and setters
@Getter
@Setter
public class Link {
    // attributes of Link
    private long id;
    private String name;
    private String url;
    private boolean isDynamic;

    private String[] tags;

    // there can be multiple departments to a link
    private Department[] departments;
    public enum Department {
        DEVOPS, FRONTEND, GAMES, PAYMENTS, PLAYERS, PROMOTIONS
    }

    // there can be multiple stages to a link
    private Stage[] stages;
    public enum Stage {
        DEVELOPMENT, STAGING, PRODUCTION
    }

}
