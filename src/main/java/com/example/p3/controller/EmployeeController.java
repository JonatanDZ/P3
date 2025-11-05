package com.example.p3.controller;

import com.example.p3.dtos.employee.EmployeeDto;
import com.example.p3.entities.Employee;
import com.example.p3.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> list = employeeService.getAllEmployees().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    // This only look for one specefic id
    @GetMapping("/id/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id)
                .map(EmployeeDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // the rest looks for list with the best match at the top.
    @GetMapping("/initials/{initials}")
    public ResponseEntity<EmployeeDto> getEmployeeByInitials(@PathVariable String initials) {
        return employeeService.getEmployeeByInitials(initials)
                .map(EmployeeDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<EmployeeDto> getEmployeeByname(@PathVariable String name) {
        return employeeService.getEmployeeByName(name)
                .map(EmployeeDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentName(@PathVariable String department) {
        List<EmployeeDto> list = employeeService.getEmployeesByDepartmentName(department)
                .stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
