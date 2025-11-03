package com.example.p3.controller;

import com.example.p3.dtos.DepartmentDto;
import com.example.p3.dtos.ToolDto;
import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.service.ToolService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

import java.util.List;

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
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartment(@PathVariable Department department) {
        List<ToolDto> list = toolService.getAllToolsByDepartment(department).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/jurisdiction/{jurisdiction}")
    public ResponseEntity<List<ToolDto>> getAllToolsByJurisdiction(@PathVariable Jurisdiction jurisdiction) {
        List<ToolDto> list = toolService.getAllToolsByJurisdiction(jurisdiction).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/stage/{stage}")
    public ResponseEntity<List<ToolDto>> getAllToolsByStage(@PathVariable Stage stage) {
        List<ToolDto> list = toolService.getAllToolsByStage(stage).stream()
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