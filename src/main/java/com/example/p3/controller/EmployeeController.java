package com.example.p3.controller;

import com.example.p3.dtos.employee.EmployeeDto;
import com.example.p3.entities.Employee;
import com.example.p3.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> list = employeeService.getAllEmployees().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    // Get by deparments missing


    // We maybe need to change the logic behind some of these.

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
    public ResponseEntity<List<EmployeeDto>> getEmployeesByInitials(@PathVariable String initials) {
        List<EmployeeDto> list = employeeService.getEmployeesByInitials(initials)
                .stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByName(@PathVariable String name) {
        List<EmployeeDto> list = employeeService.getEmployeesByName(name)
                .stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
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
