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
    private String name;
    private String url;
    private Boolean is_dynamic;

    private List<String> tags;
    private List<String> departments;
    private List<String> jurisdictions;
    private List<String> stage;

    private Boolean pending;

    @Override
    public void prepare(Tool t){
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

        this.pending = t.getPending();

    }
}