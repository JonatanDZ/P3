package com.example.p3.controller;

import com.example.p3.dtos.toolsDto.*;
import com.example.p3.entities.Tool;
import com.example.p3.service.ToolService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// This is the API http/rest controller
@RestController
// Makes "/tools" always being a part of the endpoint call

@RequestMapping("/tools")
public class ToolController {
    private final ToolService toolService;//final means that we can't change the value after it has been initialized, in this case in the controller.
    private final PersonalToolFactory  personalToolFactory;
    private final CompanyToolFactory  companyToolFactory;

    // TODO: Lombock?
    public ToolController(ToolService toolService) {
        this.toolService = toolService;
        this.personalToolFactory = new PersonalToolFactory();
        this.companyToolFactory = new CompanyToolFactory();
    }

    @Operation(
            summary = "Gets a list of all company tools in the database.",
            description = "Retrieves a list of all tools in the database, excluding pending and personal tools."
    )
    //GetMapping: indicates it is a get request on the given url
    @GetMapping("")
    //Makes a list called List and gets all tools
    public ResponseEntity<List<ToolDto>> getAllTools(){
        List<ToolDto> list = toolService.getAllToolsExcludingPending()
                //Stream makes the data into a modifiable "type"  which allows for operations like map to be performed
                .stream()
                //Map make a new array,
                //the function in map: For each tool in toolService it calls "new toolDto"
                .map(t->t.getIs_personal().equals(true)? personalToolFactory.determineTool(t) : companyToolFactory.determineTool(t))
                //Converts the new tools (in an array) into a list
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Gets all tools in a specific department.",
            description = "Retrieves a list of all tools from a department, with the department being a condition."
    )
    //Call "getAlltoolsByDepartment" which sort the tools according to the department in the URL
    @GetMapping("/department/{department}")
    //@pathVariable: get a string and inserts it into the endpoint (url)
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartment(@PathVariable String department) {
        List<ToolDto> list = toolService.getAllToolsByDepartmentName(department)
                .stream()
                .map(companyToolFactory::determineTool)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Gets a list of all tools by department, jurisdiction and stage.",
            description = "Retrieves a list of all tools by department, jurisdiction and stage. These three are all conditions. This endpoint is crucial in the display of tools on the page."
    )
    @GetMapping("department/{department}/jurisdiction/{jurisdiction}/stage/{stage}")
    public ResponseEntity<List<ToolDto>> getAllToolsByDepartmentJurisdictionStage(
            @PathVariable String department,
            @PathVariable String jurisdiction,
            @PathVariable String stage
    ){
        List<ToolDto> list = toolService.getAllToolsByDepartmentJurisdictionStage(department, jurisdiction, stage)
                .stream()
                .map(companyToolFactory::determineTool)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Uploads a tool to the database.",
            description = "POSTs a tool to the database, given the data from the add form; the single condition is an object of the Tool class."
    )
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

    @Operation(
            summary = "Gets a list of all tools by department, which are pending.",
            description = "Retrieves a list of tools with department and state of pending as conditions."
    )
    // gets list of pending tools per department
    // it is expected to pass the department of a user
    @GetMapping("/pending/department/{department}")
    public ResponseEntity<List<ToolDto>> getAllPendingToolsByUserDepartment(
            @PathVariable String department
    ){
        List<ToolDto> list = toolService.findPendingToolByUserDepartment(department)
                .stream()
                .map(companyToolFactory::determineTool)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "Deletes a pending tool.",
            description = "The endpoint deletes a tool, and does not necessarily ensure that it is pending. Returns HTTP 204 which is standard for deletions."
    )
    // delete a pending tool, in case it is declined
    // it simply deletes a tool from the tool table
    @DeleteMapping("/pending/{toolId}")
    public ResponseEntity<Void> deletePendingTool(
            @PathVariable int toolId) {
        toolService.deleteTool(toolId);
        // HTTP 204, no body. This is boilerplate for deletions
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Changes pending to false in a tool.",
            description = "Uses PUT method to alter the state of the pending attribute in a Tool, given a tool id as a condition."
    )
    // approve a pending tool
    // approving the pending tool simply reverts the pending attribute to false, making it an approved tool
    @PutMapping("/pending/{toolId}")
    public ResponseEntity<ToolDto> revertPendingAttribute(
            @PathVariable int toolId
    ){
        ToolDto toolAsArgument = companyToolFactory.determineTool(toolService.revertStateOfPending(toolId));
        return ResponseEntity.ok(toolAsArgument);
    }
}