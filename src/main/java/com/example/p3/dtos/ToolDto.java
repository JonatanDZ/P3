package com.example.p3.dtos;

import com.example.p3.model.Tool;
import lombok.Data;

// data transfer objects, to JSON
@Data
public class ToolDto {
    // attributes of Tool
    private long id;
    private String name;
    private String url;
    private boolean isDynamic;

    private String[] tags;
    // there can be multiple departments to a Tool
    private Tool.Department[] departments;

    // there can be multiple stages to a Tool
    private Tool.Stage[] stages;

    private Tool.Jurisdiction[] jurisdictions;

    public ToolDto(Tool t) {
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.isDynamic = t.isDynamic();
        this.tags = t.getTags();
        this.departments = t.getDepartments();
        this.stages = t.getStages();
        this.jurisdictions = t.getJurisdictions();
    }
}
