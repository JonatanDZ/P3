package com.example.p3.controller;

import com.example.p3.dtos.toolsDto.*;
import com.example.p3.dtos.FavoritesDto;


import com.example.p3.dtos.toolsDto.CompanyToolFactory;
import com.example.p3.dtos.toolsDto.PersonalToolFactory;
import com.example.p3.entities.Tool;

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
@RequestMapping("/tools")
public class ToolController {
    private final ToolService toolService;//final means that we can't change the value after it has been initialized
    private final PersonalToolFactory personalToolFactory;
    private final CompanyToolFactory companyToolFactory;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
        this.personalToolFactory = new PersonalToolFactory();
        this.companyToolFactory = new CompanyToolFactory();
    }

    //GetMapping: indicates it is a get request on the given url
    @GetMapping("")
    //Makes a list called List and gets all tools
    public ResponseEntity<List<ToolDto>> getAllTools(){
        //Stream makes the data into a modifiable "type"  which allows for operations like map to be performed
        List<ToolDto> list = toolService.getAllTools().stream()
                //Map make a new array,
                //the function in map: For each tool in toolService it calls "new toolDto"
                .map(t->t.getIs_personal().equals(true)? personalToolFactory.determineTool(t) : companyToolFactory.determineTool(t))
                //Converts the new tools (in an array) into a list
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getAlltoolsByDepartment" which sort the tools according to the department in the URL
    @GetMapping("/department/{department}")
    //@pathVariable: get a string and inserts it into the endpoint (url)
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartment(@PathVariable String department) {
        List<ToolDto> list = toolService.getAllToolsByDepartmentName(department).stream()
                .map(companyToolFactory::determineTool)
                .toList();
        return ResponseEntity.ok(list);
    }



    // gets list of pending tools per department
    // it is expected to pass the department of a user
    @GetMapping("/pending/department/{department}")
    public ResponseEntity<List<ToolDto>> getAllPendingToolsByUserDepartment(
            @PathVariable String department
    ){
        List<ToolDto> list = toolService.findPendingToolByUserDepartment(department).stream()
                .map(companyToolFactory::determineTool)
                .toList();
        return ResponseEntity.ok(list);
    }

    // delete a pending tool, in case it is declined
    // it simply deletes a tool from the tool table
    @DeleteMapping("/pending/{toolId}")
    public ResponseEntity<Void> deletePendingTool(
            @PathVariable int toolId) {
        toolService.deleteTool(toolId);
        // HTTP 204, no body. This is boilerplate for deletions
        return ResponseEntity.noContent().build();
    }

    // approve a pending tool
    // approving the pending tool simply reverts the pending attribute to false, making it an approved tool
    @PutMapping("/pending/{toolId}")
    public ResponseEntity<ToolDto> revertPendingAttribute(
            @PathVariable int toolId
    ){
        Tool toolAsArgument = toolService.revertStateOfPending(toolId);
        return ResponseEntity.ok(toolAsArgument);
    }

    //    @GetMapping("/jurisdiction/{jurisdiction}")
//    public ResponseEntity<List<ToolDto>> getAllToolsByJurisdiction(@PathVariable String jurisdiction) {
//        List<ToolDto> list = toolService.getAllToolsByJurisdictionName(jurisdiction).stream()
//                .map(ToolDto::new)
//                .toList();
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("/stage/{stage}")
//    public ResponseEntity<List<ToolDto>> getAllToolsByStage(@PathVariable String stage) {
//        List<ToolDto> list = toolService.getAllToolsByStageName(stage).stream()
//                .map(ToolDto::new)
//                .toList();
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("department/{department}/jurisdiction/{jurisdiction}/stage/{stage}")
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartmentJurisdictionStage(
            @PathVariable String department,
            @PathVariable String jurisdiction,
            @PathVariable String stage
    ){
        List<ToolDto> list = toolService.getAllToolsByDepartmentJurisdictionStage(department, jurisdiction, stage).stream()
                .map(companyToolFactory::determineTool)
                .toList();
        return ResponseEntity.ok(list);
    }
    //takes the data from the add form and sends it to the
    // PostMapping: indicates it is a post request on the given url
    @PostMapping("")
    //RequestBody: Gets a HTTP request (JSON) and converts it into a java object
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool){
        if (tool == null){
            return ResponseEntity.badRequest().build(); 
        }
        return ResponseEntity.ok(toolService.saveTool(tool));
    }
}