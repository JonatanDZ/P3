package com.example.p3.dtos;

import com.example.p3.model.Favorites;
import com.example.p3.model.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
public class FavoritesDto {
    private long id;
    private List<String> tools;

    public FavoritesDto(Favorites f) {
        this.id = f.getId();
        this.tools = f.getTools() == null ? List.of() : Arrays.asList(f.getTools().toString());
    }

    public static FavoritesDto from(Long key, Favorites f) {
        FavoritesDto dto = new FavoritesDto(f);
        dto.setId(key);
        return dto;
    }

}
