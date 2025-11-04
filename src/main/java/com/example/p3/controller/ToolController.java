package com.example.p3.controller;

import com.example.p3.dtos.DepartmentDto;
import com.example.p3.dtos.ToolDto;
import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.repositories.JurisdictionRepository;
import com.example.p3.service.JurisdictionService;
import com.example.p3.service.ToolService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

// This is the API http/rest controller
@RestController
@RequestMapping("/getTools")
public class ToolController {
    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("")
    public ResponseEntity<List<ToolDto>> getAllTools(){
        List<ToolDto> list = toolService.getAllTools().stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getAlltoolsByDepartment" which sort the tools according to the department in the URL
    @GetMapping("/department/{department}")
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartment(@PathVariable String department) {
        List<ToolDto> list = toolService.getAllToolsByDepartmentName(department).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/jurisdiction/{jurisdiction}")
    public ResponseEntity<List<ToolDto>> getAllToolsByJurisdiction(@PathVariable String jurisdiction) {
        List<ToolDto> list = toolService.getAllToolsByJurisdictionName(jurisdiction).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/stage/{stage}")
    public ResponseEntity<List<ToolDto>> getAllToolsByStage(@PathVariable String stage) {
        List<ToolDto> list = toolService.getAllToolsByStageName(stage).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{department}/{jurisdiction}/{stage}")
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartmentJurisdictionStage(
            @PathVariable String department,
            @PathVariable String jurisdiction,
            @PathVariable String stage
    ){
        List<ToolDto> list = toolService.getAllToolsByDepartmentJurisdictionStage(department, jurisdiction, stage).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addTool")
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool){
        if (tool ==null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(toolService.saveTool(tool));
    }
}