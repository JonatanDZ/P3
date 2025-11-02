package com.example.p3.dtos;

import com.example.p3.entities.Jurisdiction;
import lombok.Data;

@Data
public class JurisdictionDto {
    // attributes of Jurisdiction
    private long id;
    private String name;
    private boolean isDev;

    public JurisdictionDto(Jurisdiction j){
        this.id = j.getId();
        this.name = j.getJurisdictionName();
    }
}
