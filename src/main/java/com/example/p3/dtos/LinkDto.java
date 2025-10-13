package com.example.p3.dtos;
import com.example.p3.model.Link;
import lombok.Data;

// data transfer objects, to JSON
@Data
public class LinkDto {
    // attributes of Link
    private long id;
    private String name;
    private String url;
    private boolean isDynamic;

    private String[] tags;
    // there can be multiple departments to a link
    private Link.Department[] departments;

    // there can be multiple stages to a link
    private Link.Stage[] stages;

    private Link.Jurisdiction[] jurisdictions;

    public LinkDto(Link l) {
        this.id = l.getId();
        this.name = l.getName();
        this.url = l.getUrl();
        this.isDynamic = l.isDynamic();
        this.tags = l.getTags();
        this.departments = l.getDepartments();
        this.stages = l.getStages();
    }
}
