package com.example.p3.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class Favorites {
    private long id;
    private final Set<Long> toolIDs = new LinkedHashSet<>();

    public Favorites(long id) {
        this.id = id;
    }

    public boolean addTool(long toolId) {
        return toolIDs.add(toolId);
    }

    public boolean removeTool(long toolId) {
        return toolIDs.remove(toolId);
    }

    public boolean hasTool(long toolId) {
        return toolIDs.contains(toolId);
    }
}


