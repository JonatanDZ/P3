package com.example.p3.controller;

import com.example.p3.dtos.ToolDto;
import com.example.p3.model.Tool;
import com.example.p3.service.ToolService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/getToolsByStage/{stage}")
    public ResponseEntity<List<ToolDto>> getToolsByStage(@PathVariable("stage") String stage){
        List<ToolDto> list = toolService.getToolsByStage(stage);
        return ResponseEntity.ok(list);
    }

    // maybe change this to department/jurisdiction/stage or implement new endpoint
    @GetMapping("/jurisdiction/{jurisdiction}")
    public ResponseEntity<List<ToolDto>> getByJurisdiction(@PathVariable Tool.Jurisdiction jurisdiction) {
        List<ToolDto> list = toolService.findByJurisdiction(jurisdiction).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getAlltoolsByDepartment" which sort the tools according to the department in the URL
  /*  @GetMapping("/department/{department}")
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartment(@PathVariable Tool.Department department) {
        List<ToolDto> list = toolService.getAllToolsByDepartment(department).values().stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{department}/{jurisdiction}/{stage}")
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartmentJurisdictionStage(
            @PathVariable Tool.Department department,
            @PathVariable Tool.Jurisdiction jurisdiction,
            @PathVariable Tool.Stage stage
    ){
        List<ToolDto> list = toolService.getAllToolsByDepartmentJurisdictionStage(department, jurisdiction, stage).values().stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addTool")
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool){
        if (tool ==null){
            return ResponseEntity.badRequest().build();
        }

        Tool createdTool = toolService.createTool(
                null,
                tool.getName(),
                tool.getUrl(),
                tool.tagsToString(),
                tool.getDepartments(),
                tool.getStages(),
                tool.getJurisdictions(),
                tool.isDynamic()
        );

        return ResponseEntity.ok(createdTool);
    }*/
}