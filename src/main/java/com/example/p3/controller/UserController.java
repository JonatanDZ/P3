package com.example.p3.controller;

import com.example.p3.dtos.UserDto;
import com.example.p3.model.User;
import com.example.p3.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getUsers")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
                this.userService = userService;
    }
//Default
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> list = userService.getAllUsers().values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<UserDto>> getAllUsersByDepartment(@PathVariable User.Department department) {
        List<UserDto> list = userService.getAllUsersByDepartment(department).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getUserById" which selects the user according to the id (skal være initialer) in the URL
    @GetMapping("/id/{id}")
    public ResponseEntity<List<UserDto>> getUserById(@PathVariable long id) {
        List<UserDto> list = userService.getUserById(id).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/initials/{initials}")
    public ResponseEntity<List<UserDto>> getUserByInitials(@PathVariable String initials) {
        List<UserDto> list = userService.getUserByInitials(initials).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserDto>> getUserByEmail(@PathVariable String email) {
        List<UserDto> list = userService.getUserByEmail(email).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> getUserByName(@PathVariable String name) {
        List<UserDto> list = userService.getUserByName(name).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
