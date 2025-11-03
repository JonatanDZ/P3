package com.example.p3.service;


import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;

    public List<com.example.p3.entities.Tool> getAllTools() {
        return toolRepository.findAll();
    }

    //Filters the tools so only tool with the department from the URL is returned
    public List<com.example.p3.entities.Tool> getAllToolsByDepartment(Department department) {
        return getAllTools().stream()
                .filter(tool -> tool.getDepartments().contains(department))
                .collect(Collectors.toList());
    }

    public List<com.example.p3.entities.Tool> getAllToolsByJurisdiction(Jurisdiction jurisdiction) {
        return getAllTools().stream()
                .filter(tool -> tool.getJurisdictions().contains(jurisdiction))
                .collect(Collectors.toList());
    }

    public List<com.example.p3.entities.Tool> getAllToolsByStage(Stage stage) {
        return getAllTools().stream()
                .filter(tool -> tool.getStages().contains(stage))
                .collect(Collectors.toList());
    }

    public Tool saveTool(Tool tool) {
        return toolRepository.saveAndFlush(tool);
    }

}
