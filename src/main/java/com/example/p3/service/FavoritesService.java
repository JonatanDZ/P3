package com.example.p3.service;

import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoritesService {
    private final ToolRepository toolRepository;

    public List<Tool> getFavorites(String employeeInitials, String jurisdiction, String stage) {
        return toolRepository.findFavoritesByEmployeeAndJurisdictionAndStage(employeeInitials, jurisdiction, stage);
    }
}
