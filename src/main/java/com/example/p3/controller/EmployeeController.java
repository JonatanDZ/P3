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
}

/*
package com.example.p3.controller;

import com.example.p3.dtos.employee.EmployeeDto;
import com.example.p3.model.employee.Employee;
import com.example.p3.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getEmployees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Default
    @GetMapping()
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> list = employeeService.getAllEmployees().values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesByDepartment(@PathVariable Employee.Department department) {
        List<EmployeeDto> list = employeeService.getAllEmployeesByDepartment(department).values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getEmployeeById" which selects the Employee according to the id (skal være initialer) in the URL
    @GetMapping("/id/{id}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeById(@PathVariable long id) {
        List<EmployeeDto> list = employeeService.getEmployeeById(id).values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/initials/{initials}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByInitials(@PathVariable String initials) {
        List<EmployeeDto> list = employeeService.getEmployeeByInitials(initials).values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByEmail(@PathVariable String email) {
        List<EmployeeDto> list = employeeService.getEmployeeByEmail(email).values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByName(@PathVariable String name) {
        List<EmployeeDto> list = employeeService.getEmployeeByName(name).values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}


    /* Potentiel samlet function?
    @GetMapping("/{infoType}/{info}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeInfo(@PathVariable String infoType, @PathVariable String info) {
        List<EmployeeDto> list = EmployeeService.getEmployeeInfo(infoType,info).values().stream()
                .map(EmployeeDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
     */
