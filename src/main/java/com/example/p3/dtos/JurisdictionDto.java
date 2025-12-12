package com.example.p3.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Tool;

import lombok.Data;

//@Data is a combination of several Lombok annotations
@Data
public class JurisdictionDto {
    // attributes of Jurisdiction
    private long id;
    private String name;
    private List<String> tools;

    public JurisdictionDto(Jurisdiction j){
        this.id = j.getId();
        this.name = j.getName();
        // Checks all tools for jurisdiction and adds it to the jurisdiction list.
        this.tools = j.getJurisdictionTools()
                .stream().
                map(Tool::getName).
                collect(Collectors.toList());
    }
}