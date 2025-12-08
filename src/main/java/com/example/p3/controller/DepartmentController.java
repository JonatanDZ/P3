package com.example.p3.controller;

import com.example.p3.dtos.DepartmentDto;
import com.example.p3.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


// This is the API http/rest controller
@RestController
// Makes "/departments" always being a part of the endpoint call
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // this is an annotation used for swagger. It allows for understanding the endpoint at a quick glance
    @Operation(
            summary = "Gets a list of all departments",
            description = "Returns every department in the database in the form of a list."
    )
    // The endpoint is empty and will run if "/departments" are called
    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        // Calls service to get all departments
        List<DepartmentDto> list = departmentService.getAllDepartments()
                // Turns it into a stream to be processed with the streams API
                .stream()
                // For each department in the stream, a new departmentDto is created
                .map(DepartmentDto::new)
                // Collects the mapped stream and puts it into List<DepartmentDto>
                .toList();
        return ResponseEntity.ok(list);
    }


}
