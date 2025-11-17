package com.example.p3.dtos;

import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.entities.Tag;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

// data transfer objects, to JSON
@Data
public class ToolDto {
    // attributes of Tool
    private Integer id;
    private String name;
    private String url;
    private Boolean is_dynamic;
    private List<String> tags;
    private List<String> departments;
    private List<String> jurisdictions;
    private List<String> stage;

    //Takes the entities from the database and converts it into objects of the type tool
    public ToolDto(Tool t) {
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.tags = t.getTags().stream()
                .map(Tag::getValue)
                .collect(Collectors.toList());


        this.is_dynamic = t.getIs_dynamic();

        this.departments = t.getDepartments().stream()
                .map(Department::getName)
                .collect(Collectors.toList());

        this.jurisdictions = t.getJurisdictions().stream()
                .map(Jurisdiction::getName)
                .collect(Collectors.toList());

        this.stage = t.getStages().stream()
                .map(Stage::getName)
                .collect(Collectors.toList());


    }
}