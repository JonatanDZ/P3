package com.example.p3.service;


import com.example.p3.entities.*;
import com.example.p3.repositories.ToolRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.Optional;


@Service
@AllArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Optional<Tool> getToolById(Integer tool_id){ return toolRepository.findById(tool_id); }

    //Filters the tools so only tool with the department from the URL is returned
    public List<Tool> getAllToolsByDepartmentName(String departmentName) {
      return toolRepository.findByDepartments_DepartmentName(departmentName);
    }

    public List<Tool> getAllToolsByJurisdictionName(String jurisdictionName) {
        return toolRepository.findByJurisdictions_JurisdictionName(jurisdictionName);
    }

    public List<Tool> getAllToolsByStageName(String stageName) {
        return toolRepository.findByStages_Name(stageName);
    }

    public List<Tool> getAllToolsByDepartmentJurisdictionStage(String department, String jurisdiction, String stage){
        return toolRepository.findByDepartments_DepartmentNameAndJurisdictions_JurisdictionNameAndStages_Name(department, jurisdiction, stage);
    }

    public Tool saveTool(Tool tool) {
        return toolRepository.saveAndFlush(tool);
    }


}