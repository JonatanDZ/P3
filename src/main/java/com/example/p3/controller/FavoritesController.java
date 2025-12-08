package com.example.p3.controller;

import com.example.p3.dtos.FavoritesDto;
import com.example.p3.service.FavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class FavoritesController {
    private final FavoritesService favoritesService;

    @Operation(
            summary = "Gets the favorite list of a user given user given proper conditions.",
            description = "Retrieves the favorite list of a user given user initials, current jurisdiction and current stage."
    )
    // This endpoint makes sure that we only hit the API/server once, and retrieves the favorite list of a user given certain conditions
    // Scenario: User (id=1) toggles jurisdiction = DK, stage = PRODUCTION.
    // The URL/endpoint will look like: /employee/{employeeInitials}/favorites?jurisdiction=DK&stage=PRODUCTION
    @GetMapping("/{employee-initials}/favorites")
    public ResponseEntity<List<FavoritesDto>> getFavorites(
            @PathVariable(name = "employee-initials") String employeeInitials,
            @RequestParam String jurisdiction,
            @RequestParam String stage
    ) {
        List<FavoritesDto> list = favoritesService.getFavorites(employeeInitials, jurisdiction, stage).stream()
                .map(FavoritesDto::new)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Makes a tool into a favorite tool of a user.",
            description = "Makes a tool into a favorite tool of a user given employee initials and the id of a tool."
    )
    // Toggle an existing tool as a favorite
    @PostMapping("/{employee-initials}/favorites/{tool-id}")
    public ResponseEntity<FavoritesDto> toggleFavorite(
            @PathVariable(name = "employee-initials") String employeeInitials,
            @PathVariable(name = "tool-id") int toolId
    ){
        // this should simply call the method toggleFavorite
        FavoritesDto toolToggled = new FavoritesDto(favoritesService.toggleFavorite(employeeInitials, toolId));
        return ResponseEntity.ok(toolToggled);
    }

    @Operation(
            summary = "Turns a favorite tool into a non favorite tool.",
            description = "Removes a favorite tool from the favorite tool table given the employee initials and tool id; not the entire database."
    )
    // remove an existing tool as a favorite
    // this assumes that every tool is accessible to all.
    @DeleteMapping("/{employee-initials}/favorites/{tool-id}")
    public ResponseEntity<FavoritesDto> untoggleFavorite(
            @PathVariable(name = "employee-initials") String employeeInitials,
            @PathVariable(name = "tool-id") int toolId
    ){
        FavoritesDto toolUntoggled = new FavoritesDto(favoritesService.untoggleFavorite(employeeInitials, toolId));
        return ResponseEntity.ok(toolUntoggled);
    }
}

