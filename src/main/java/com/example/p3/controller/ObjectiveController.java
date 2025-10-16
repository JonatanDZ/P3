package com.example.p3.controller;


import com.example.p3.dtos.ToolDto;
import com.example.p3.dtos.UserDto;
import com.example.p3.model.Tool;
import com.example.p3.model.User;
import com.example.p3.service.ToolService;
import com.example.p3.service.UserService;
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
    private final UserService userService;

    public ObjectiveController(ToolService toolService, UserService userService) {
        this.toolService = toolService;
        this.userService = userService;
    }

    ////////////////////////////////////////////

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> list = userService.getAllUsers().values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getUsers/{department}")
    public ResponseEntity<List<UserDto>> getAllUsersByDepartment(@PathVariable User.Department department) {
        List<UserDto> list = userService.getAllUsersByDepartment(department).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }


    ///////////////////////////////////////


    @GetMapping("/getTools")
    public ResponseEntity<List<ToolDto>> getAlltools(){
        List<ToolDto> list = toolService.getAlltools().values().stream()
                .map(ToolDto::new)
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

    /// /////////////////ASTA start///////////////////
    //Call "getUserById" which selects the user according to the id (skal være initialer) in the URL
    @GetMapping("/getUser/{id}")
    public ResponseEntity<List<UserDto>> getUserById(@PathVariable long id) {
        List<UserDto> list = userService.getUserById(id).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
    /// /////////////////ASTA slut///////////////////
    
}