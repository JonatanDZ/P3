package com.example.p3.controller;

import com.example.p3.dtos.FavoritesDto;
import com.example.p3.service.FavoritesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("")
public class FavoritesController {
    private final FavoritesService favoritesService;

    // This endpoint makes sure that we only hit the API/server once, and retrieves the favorite list of a user given certain conditions
    // Scenario: User (id=1) toggles jurisdiction = DK, stage = PRODUCTION.
    // The URL/endpoint will look like: /users/{userId}/favorites?jurisdiction=DK&stage=PRODUCTION
    @GetMapping("/employees/{employeeInitials}/favorites")
    public ResponseEntity<List<FavoritesDto>> getFavorites(
            @PathVariable String employeeInitials,
            @RequestParam(required = false) String jurisdiction,
            @RequestParam(required = false) String stage
    ) {
        List<FavoritesDto> list = favoritesService.getFavorites(employeeInitials, jurisdiction, stage).stream()
                .map(FavoritesDto::new)
                .toList();
        return ResponseEntity.ok().body(list);
    }
}

