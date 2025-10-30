package com.example.p3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Favorites {
    private long id;

    @JsonProperty("toolIDs")
    private final Set<Long> toolIDs = new LinkedHashSet<>();

    public Favorites(long id) {
        this.id = id;
    }

//    public boolean addTool(long toolId) {
//        return toolIDs.add(toolId);
//    }
//
//    public boolean removeTool(long toolId) {
//        return toolIDs.remove(toolId);
//    }
//
//    public boolean hasTool(long toolId) {
//        return toolIDs.contains(toolId);
//    }
}


