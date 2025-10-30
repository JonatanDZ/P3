package com.example.p3.dtos;

import com.example.p3.model.Favorites;
import lombok.Data;

import java.util.*;

@Data
public class FavoritesDto {
    private long id;
    private List<Long> toolIDs;

    public FavoritesDto(Favorites f) {
        this.id = f.getId();
        this.toolIDs = f.getToolIDs() == null ? List.of() : new ArrayList<>(f.getToolIDs());
    }

    public static FavoritesDto from(Long key, Favorites f) {
        FavoritesDto dto = new FavoritesDto(f);
        dto.setId(key);
        return dto;
    }

}
