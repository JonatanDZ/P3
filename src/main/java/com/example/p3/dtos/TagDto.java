package com.example.p3.dtos;

import com.example.p3.dtos.toolsDto.ToolUrlName;
import com.example.p3.entities.Tag;

import lombok.Data;

import java.util.List;

//@Data is a combination of several Lombok annotations
@Data
public class TagDto {
    // Attributes of a tag
    private Integer id;
    private String value;
    private List<ToolUrlName> tools;

    public TagDto(Tag t) {
        this.id = t.getId();
        this.value = t.getValue();
        // Checks all tools for tags and adds it to the tag list.
            // stream API collect method. It converts a set to a list. Below are all conversions from a set (in entities) to a list (DTO)
            // it also maps to the name field in order to avoid infinite object recursion.
        this.tools = t.getTagTools()
                .stream()
                .map(Tool -> new ToolUrlName(Tool.getName(), Tool.getUrl(), Tool.getIs_dynamic()))
                .toList();
    }
}