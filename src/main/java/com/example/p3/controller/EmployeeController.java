package com.example.p3.controller;

import com.example.p3.dtos.EmployeeDto;
import com.example.p3.entities.Employee;
import com.example.p3.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    //This is an annotation used for swagger. It allows for understanding the endpoint at a quick glance
    @Operation(
            summary = "Get employee by initials",
            description = "Looks up an employee based on their initials. Initials must be 2–4 letters, including Danish characters (Æ, Ø, Å)."
    )
    // "{initials}" is the path variable the endpoint will take
    @GetMapping("/initials/{initials}")
    public ResponseEntity<EmployeeDto> getEmployeeByInitials(@PathVariable String initials) {
        //Reject invalid input before calling the service
        //Check if the given initials do not match the pattern only letters from A-Å upper and lower case and only 2-4 characters
        if (!initials.matches("^[A-Za-zÆØÅæøå]{2,4}$")){
            return ResponseEntity.badRequest().build();
        }
        // Calls employee service to get the employee by initials
        Employee employee = employeeService.getEmployeeByInitials(initials).orElse(null);

        //If the provided employee initials in the path variable is null it will return Not Found
        if  (employee == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new EmployeeDto(employee));
        }
    }
}