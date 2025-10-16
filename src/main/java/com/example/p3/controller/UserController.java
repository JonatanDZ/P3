package com.example.p3.controller;

import com.example.p3.dtos.UserDto;
import com.example.p3.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
                this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> list = userService.getAllUsers().values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
