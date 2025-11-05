package com.example.p3.controller;

import com.example.p3.dtos.FavoritedDetailDto;
import com.example.p3.dtos.FavoritesDto;
import com.example.p3.dtos.ToolDto;
import com.example.p3.service.FavoritesService;
import com.example.p3.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/getTools")
public class FavoritesController {
    private final FavoritesService favoritesService;
    private final ToolService toolService;

    public FavoritesController(ToolService toolService, FavoritesService favoritesService) {
        this.toolService = toolService;
        this.favoritesService = favoritesService;
    }

    //this endpoint gets all the favorite lists for all users, but just with user id and ids for the tools
    @GetMapping("/getFavoriteTools")
    public ResponseEntity<List<FavoritesDto>> getFavorites(){
        List<FavoritesDto> list = favoritesService.getFavorites().values().stream()
                .map(FavoritesDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //this endpoint call all the favorite lists for all users, including the objects of the tools
  /*  @GetMapping("/getFavoriteTools/details")
    public ResponseEntity<List<FavoritedDetailDto>> getFavoriteTools(){
        var allTools = toolService.getAllTools();
        var list = favoritesService.getFavorites().values().stream()
                .map(f -> new FavoritedDetailDto(
                        f.getId(),
                        f.getToolIDs().stream()
                                .map(allTools::get)
                                .filter(t -> t != null)
                                .map(ToolDto::new)
                                .toList()
                ))
                .toList();
        return ResponseEntity.ok(list);
    }

    //You can here call the favorites for a specific user, but just their id and the id's of their tools
    @GetMapping("/getFavoriteTools/{id}")
    public ResponseEntity<FavoritesDto> getFavoriteById(@PathVariable long id) {
        var favorite = favoritesService.getFavorites().get(id);

        if (favorite == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new FavoritesDto(favorite));
    }

    //You can here call the favorites for a specific user including the tools as objects
    @GetMapping("/getFavoriteTools/details/{id}")
    public ResponseEntity<FavoritedDetailDto> getFavoriteDetailById(@PathVariable long id) {
        var favorite = favoritesService.getFavorites().get(id);

        if (favorite == null) {
            return ResponseEntity.notFound().build();
        }

        var allTools = toolService.getAllTools();

        var favorites = new FavoritedDetailDto(
                favorite.getId(),
                favorite.getToolIDs().stream()
                        .map(allTools::get)
                        .filter(Objects::nonNull)
                        .map(ToolDto::new)
                        .toList()
        );

        return ResponseEntity.ok(favorites);
    }*/

}
