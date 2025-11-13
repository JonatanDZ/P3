package com.example.p3.controller;

import com.example.p3.dtos.toolsDto.CompanyToolDto;

import com.example.p3.dtos.toolsDto.ToolDtoCLASS;
import com.example.p3.dtos.toolsDto.ToolInterface;
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
@RequestMapping("/getTools")
public class ToolController {
    private final ToolService toolService; //final means that we can't change the value after it has been initialized

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    //GetMapping: indicates it is a get request on the given url
    @GetMapping("")
    //Makes a list called List and gets all tools
    public ResponseEntity<List<CompanyToolDto>> getAllTools(){
        //Stream makes the data into a modifiable "type"  which allows for operations like map to be performed
        List<CompanyToolDto> list = toolService.getAllTools().stream()
                //Map make a new array,
                //the function in map: For each tool in toolService it calls "new toolDto"
                .map(CompanyToolDto::new)
                //Converts the new tools (in an array) into a list
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getAlltoolsByDepartment" which sort the tools according to the department in the URL
    @GetMapping("/department/{department}")
    //@pathVariable: get a string and inserts it into the endpoint (url)
    public ResponseEntity<List<CompanyToolDto>> getAllToolsByDepartment(@PathVariable String department) {
        List<CompanyToolDto> list = toolService.getAllToolsByDepartmentName(department).stream()
                .map(CompanyToolDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/jurisdiction/{jurisdiction}")
    public ResponseEntity<List<ToolDtoCLASS>> getAllToolsByJurisdiction(@PathVariable String jurisdiction) {
        List<ToolInterface> list = toolService.getAllToolsByJurisdictionName(jurisdiction).stream()
                .map(ToolDtoCLASS::new)
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
    //takes the data from the add form and sends it to the
    // PostMapping: indicates it is a post request on the given url
    @PostMapping("/addTool")
    //RequestBody: Gets a HTTP request (JSON) and converts it into a java object
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool){
        if (tool == null){
            return ResponseEntity.badRequest().build(); 
        }
        return ResponseEntity.ok(toolService.saveTool(tool));
    }
}