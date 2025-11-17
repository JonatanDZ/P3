package com.example.p3.service;

import com.example.p3.entities.*;
import com.example.p3.repositories.ToolRepository;

//Generates constructor for every field in a class automatically
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    //Filters the tools so only tool with the department from the URL is returned
    public List<Tool> getAllToolsByDepartmentName(String departmentName) {
      return toolRepository.findByDepartments_Name(departmentName);
    }

//    public List<Tool> getAllToolsByJurisdictionName(String jurisdictionName) {
//        return toolRepository.findByJurisdictions_JurisdictionName(jurisdictionName);
//    }
//
//    public List<Tool> getAllToolsByStageName(String stageName) {
//        return toolRepository.findByStages_Name(stageName);
//    }

    public List<Tool> getAllToolsByDepartmentJurisdictionStage(String department, String jurisdiction, String stage){
        return toolRepository.findByDepartments_NameAndJurisdictions_NameAndStages_Name(department, jurisdiction, stage);
    }

    public Tool saveTool(Tool tool) {
        return toolRepository.save(tool);
    }

    public List<String> searchToolByTag(String tag) {
        ///Return a JSON/array with arrays containing all tools with the tag (include) og give the url and name.
        /// ændre under tgsDto og få den til at hente url også. Lav et mini tool objekt.


    }

}