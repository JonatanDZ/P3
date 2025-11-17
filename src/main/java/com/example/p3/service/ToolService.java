package com.example.p3.service;

import com.example.p3.entities.*;
import com.example.p3.repositories.ToolRepository;

//Generates constructor for every field in a class automatically
import jakarta.transaction.Transactional;
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
        return toolRepository.saveAndFlush(tool);
    }

    public List<Tool> findPendingToolByUserDepartment(String departmentName){
        return toolRepository.findPendingToolByUserDepartment(departmentName);
    }

    // delete Pending Tool
    @Transactional
    public void deleteTool(int toolId) {
        toolRepository.deleteById(toolId);
    }

    // revert state of pending for a tool
    // this is what happens when a pending tool is approved. They already exist in the tool list.
    @Transactional
    public Tool revertStateOfPending(int toolId) {
        toolRepository.revertStateOfPending(toolId);
        return toolRepository.findById(toolId).orElseThrow();
    }

}