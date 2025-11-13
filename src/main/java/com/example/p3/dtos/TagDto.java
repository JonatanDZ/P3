package com.example.p3.dtos;

import com.example.p3.entities.Tag;
import com.example.p3.entities.Tool;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TagDto {
    private Integer id;
    private String value;
    private List<String> tools;

    public TagDto(Tag t) {
        this.id = t.getId();
        this.value = t.getValue();
        this.tools = t.getTagTools().stream()
                .map(Tool::getName)
                .collect(Collectors.toList());
    }
}
