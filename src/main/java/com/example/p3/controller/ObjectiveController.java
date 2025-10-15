package com.example.p3.controller;


import com.example.p3.dtos.FavoritesDto;
import com.example.p3.dtos.ToolDto;
import com.example.p3.model.Tool;
import com.example.p3.service.FavoritesService;
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
@RequestMapping()
public class ObjectiveController {
    private final ToolService toolService;
    private final FavoritesService favoritesService;

    public ObjectiveController(ToolService toolService, FavoritesService favoritesService) {
        this.toolService = toolService;
        this.favoritesService = favoritesService;
    }

    @GetMapping("/getTools")
    public ResponseEntity<List<ToolDto>> getAlltools(){
        List<ToolDto> list = toolService.getAlltools().values().stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getFavoriteTools")
    public ResponseEntity<List<FavoritesDto>> getFavorites(){
        List<FavoritesDto> list = favoritesService.getFavorites().values().stream()
                .map(FavoritesDto::new)
                .toList();
        return ResponseEntity.ok(list);

    }

    @PostMapping("/getToolsByStage/{stage}")
    public ResponseEntity<List<ToolDto>> gettoolsByStage(@PathVariable("stage") String stage){
        List<ToolDto> list = toolService.gettoolsByStage(stage);
        return ResponseEntity.ok(list);
    }
    // maybe change this to department/jurisdiction/stage or implement new endpoint

    @GetMapping("/getTools/{jurisdiction}")

    public ResponseEntity<List<ToolDto>> getByJurisdiction(@PathVariable Tool.Jurisdiction jurisdiction) {
        List<ToolDto> list = toolService.findByJurisdiction(jurisdiction).stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
    //Call "getAlltoolsByDepartment" which sort the tools according to the department in the URL

    @GetMapping("/getTools/{department}")
    public ResponseEntity<List<ToolDto>> getAlltoolsByDepartment(@PathVariable Tool.Department department) {
        List<ToolDto> list = toolService.getAlltoolsByDepartment(department).values().stream()
                .map(ToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getTools/{department}/{jurisdiction}/{stage}")
    public ResponseEntity<List<ToolDto>> getAlltoolsByDepartmentJurisdictionStage(
            @PathVariable Tool.Department department,
            @PathVariable Tool.Jurisdiction jurisdiction,
            @PathVariable Tool.Stage stage
    ){
        List<ToolDto> list = toolService.getAlltoolsByDepartmentJurisdictionStage(department, jurisdiction, stage).values().stream()
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
    }


    }



