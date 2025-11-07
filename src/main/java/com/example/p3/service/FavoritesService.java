package com.example.p3.service;

import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoritesService {
    private final ToolRepository toolRepository;

    public List<Tool> getFavorites(String employeeInitials, String jurisdiction, String stage) {
        return toolRepository.findFavoritesByEmployeeAndJurisdictionAndStage(employeeInitials, jurisdiction, stage);
    }

    // Given that a tool, which already exists in favorites, is toggled create a custom exception?
    // right now the query handles no duplicates
    // it has to be transactional else it throws an error. This just ensures the opening and closing of the connection to db
    @Transactional
    public Tool toggleFavorite(String employeeInitials, int toolId) {
        //  make sure the tool actually exists
        if (!toolRepository.existsById(toolId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tool does not exist");
        }

        toolRepository.toggleToolAsFavorite(employeeInitials, toolId);
        return toolRepository.findById(toolId).orElseThrow();
    }
}
