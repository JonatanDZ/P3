package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;

import lombok.Getter;

import com.example.p3.entities.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CompanyToolDto implements ToolDto {
    private Integer id;
    private Boolean is_personal = false;
    private String name;
    private String url;
    private Boolean is_dynamic;
    private String created_by;
    private Boolean pending;
    private List<String> tags;
    private List<String> departments;
    private List<String> jurisdictions;
    private List<String> stage;

    @Override
    public void prepare(Tool t){
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.pending = t.getPending();
        this.tags = t.getTags()
                // Checks all Company tools for tags and adds it to the tag list.
                    // stream API collect method. It converts a set to a list. Below are all conversions from a set (in entities) to a list (DTO)
                    // it also maps to the name field in order to avoid infinite object recursion.
                .stream()
                .map(Tag::getValue)
                .collect(Collectors.toList());

        this.is_dynamic = t.getIs_dynamic();

        // gets an employee object, then we use the getter from Employee
        // this checks if the employee object got is null, if true the row should be null, else it should get the initials
        // if this is not present it will create issues in displaying tools and destroy the UI !!!
        this.created_by = t.getCreated_by() == null ? null : t.getCreated_by().getInitials();

        this.departments = t.getDepartments()
                .stream()
                .map(Department::getName)
                .collect(Collectors.toList());

        this.jurisdictions = t.getJurisdictions()
                .stream()
                .map(Jurisdiction::getName)
                .collect(Collectors.toList());

        this.stage = t.getStages()
                .stream()
                .map(Stage::getName)
                .collect(Collectors.toList());
    }
}