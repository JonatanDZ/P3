package com.example.p3.dtos;

import com.example.p3.entities.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

// data transfer objects, to JSON
@Data
public class FavoritesDto {
    // attributes of Tool
    private long id;
    private String name;
    private String url;
    private Boolean isPersonal;
    private Boolean isDynamic;
    private List<String> departments;
    private List<String> stages;
    private List<String> jurisdictions;
    private List<String> tags;

    public FavoritesDto(Tool t) {
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.isDynamic = t.getIsDynamic();
        this.isPersonal = t.getIsPersonal();
        // stream API collect method. It converts a set to a list. Below are all conversions from a set (in entities) to a list (DTO)
        // it also maps to the name field in order to avoid infinite object recursion.
        this.departments = t.getDepartments().stream()
                .map(Department::getDepartmentName)
                .collect(Collectors.toList());

        this.stages = t.getStages().stream()
                .map(Stage::getName)
                .collect(Collectors.toList());

        this.jurisdictions = t.getJurisdictions().stream()
                .map(Jurisdiction::getJurisdictionName)
                .collect(Collectors.toList());

        this.tags = t.getTags().stream()
                .map(Tag::getValue) // or getName(), depending on your Tag entity
                .collect(Collectors.toList());
    }
}
