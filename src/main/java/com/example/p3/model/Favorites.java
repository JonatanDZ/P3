package com.example.p3.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class Favorites {
    private long id;
    private Set<String> tools = new LinkedHashSet<>();

    public boolean addTool(String tool) {
        return tools.add(tool);
    }

    public boolean removeTool(String tool) {
        return tools.remove(tool);
    }

    public boolean hasTool(String tool) {
        return tools.contains(tool);
    }
}
