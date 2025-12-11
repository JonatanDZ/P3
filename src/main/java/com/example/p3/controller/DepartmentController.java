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

    // DepartmentService must be assigned once in the constructor and can't be changed later.
    // This is the object that talks to the service layer.
    private final DepartmentService departmentService;

    // TODO: We should remove this and use lombok like every other controller.
    // The DepartmentControllers constructor.
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // This is an annotation used for swagger. It allows for understanding the endpoint at a quick glance
    @Operation(
            summary = "Gets a list of all departments",
            description = "Returns every department in the database in the form of a list."
    )
    // The endpoint is empty and will run if "/departments" are called
    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        // Calls service to get all departments
        // Fetch all department entities and convert them to DTOs
        List<DepartmentDto> list = departmentService.getAllDepartments()
                // Converts the list returned by the service into a stream so we can use the Streams API (map, filter, etc.).
                .stream()
                // For each department(department entity) in the stream, a new departmentDto is created with the DepartmentDto constructor.
                .map(DepartmentDto::new)
                // Collects the mapped stream and puts it into List<DepartmentDto>
                .toList();
        // Returns a list of departmentDTOs wrapped in a ResponseEntity to control HTTP status, headers, etc.
        return ResponseEntity.ok(list);
    }
}
