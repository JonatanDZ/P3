package com.example.p3.controller;

import com.example.p3.dtos.DepartmentDto;
import com.example.p3.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


// This is the API http/rest controller
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> list = departmentService.getAllDepartments().stream()
                .map(DepartmentDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }


}
