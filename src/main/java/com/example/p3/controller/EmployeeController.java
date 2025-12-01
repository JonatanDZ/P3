package com.example.p3.controller;

import com.example.p3.dtos.EmployeeDto;
import com.example.p3.entities.Employee;
import com.example.p3.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;




    // the rest looks for list with the best match at the top.
    @GetMapping("/initials/{initials}")
    public ResponseEntity<EmployeeDto> getEmployeeByInitials(@PathVariable String initials) {
        //Reject invalid input before calling the service
        //Check if the given initials do not match the pattern only letters from A-Å upper and lower case and only 2-4 characters
        if (!initials.matches("^[A-Za-zÆØÅæøå]{2,4}$")){
            return ResponseEntity.badRequest().build();
        }
        Employee employee = employeeService.getEmployeeByInitials(initials).orElse(null);
        if  (employee == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new EmployeeDto(employee));
        }
    }
}

/*

    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> list = employeeService.getAllEmployees().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    //Hvad er use casen?
    @GetMapping("/name/{name}")
    public ResponseEntity<EmployeeDto> getEmployeeByname(@PathVariable String name) {
        Employee employee = employeeService.getEmployeeByName(name).orElse(null);
        if  (employee == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new EmployeeDto(employee));
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartmentName(@PathVariable String department) {
        List<EmployeeDto> list = employeeService.getEmployeesByDepartmentName(department)
                .stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
 */