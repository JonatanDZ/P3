package com.example.p3.model;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// adding getters and setters
@Getter
@Setter
public class Tool {
    // attributes of Tool
    private long id;
    private String name;
    private String url;
    private boolean isDynamic;

    private String[] tags;

    // there can be multiple departments to a Tool
    private Department[] departments;
   public enum Department {
        DEVOPS, FRONTEND, GAMES, PAYMENTS, PLAYERS, PROMOTIONS, HR, LEGAL
    }

    // there can be multiple stages to a Tool
    private Stage[] stages;
    public enum Stage {
        DEVELOPMENT, STAGING, PRODUCTION
    }


    private Jurisdiction[] jurisdictions;
    public enum Jurisdiction {
        DK, UK
    }


    public String tagsToString(){
        // This is a ternary operator (just a short way to write if/else)
        return String.join(", ", this.getTags() != null ? this.getTags() : new String[]{});
    }

}
