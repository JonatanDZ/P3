package com.example.p3.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class Favorites {
    private long id;
    private Set<Long> toolIDs = new LinkedHashSet<>();

}
