package com.example.p3.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FavoritedDetailDto {
    private long id;
    private List<ToolDto> toolIDs;

    public FavoritedDetailDto(long id, List<ToolDto> tools) {
        this.id = id;
        this.toolIDs = tools;
    }
}
