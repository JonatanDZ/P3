package com.example.p3.dtos;

import com.example.p3.dtos.toolsDto.ToolUrlName;
import com.example.p3.entities.Tag;

import lombok.Data;

import java.util.List;

@Data
public class TagDto {
    // Attributes of a tag
    private Integer id;
    private String value;
    private List<ToolUrlName> tools;

    public TagDto(Tag t) {
        this.id = t.getId();
        this.value = t.getValue();
        this.tools = t.getTagTools()
                .stream()
                .map(Tool -> new ToolUrlName(Tool.getName(), Tool.getUrl(), Tool.getIs_dynamic()))
                .toList();
    }
}