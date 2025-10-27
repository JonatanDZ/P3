package com.example.p3.controller;

import com.example.p3.dtos.PersonDto;
import com.example.p3.model.Person;
import com.example.p3.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getUsers")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //Default
    @GetMapping()
    public ResponseEntity<List<PersonDto>> getAllUsers() {
        List<PersonDto> list = personService.getAllUsers().values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<PersonDto>> getAllUsersByDepartment(@PathVariable Person.Department department) {
        List<PersonDto> list = personService.getAllUsersByDepartment(department).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getUserById" which selects the user according to the id (skal være initialer) in the URL
    @GetMapping("/id/{id}")
    public ResponseEntity<List<PersonDto>> getUserById(@PathVariable long id) {
        List<PersonDto> list = personService.getUserById(id).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/initials/{initials}")
    public ResponseEntity<List<PersonDto>> getUserByInitials(@PathVariable String initials) {
        List<PersonDto> list = personService.getUserByInitials(initials).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<PersonDto>> getUserByEmail(@PathVariable String email) {
        List<PersonDto> list = personService.getUserByEmail(email).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<PersonDto>> getUserByName(@PathVariable String name) {
        List<PersonDto> list = personService.getUserByName(name).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}


    /* Potentiel samlet function?
    @GetMapping("/{infoType}/{info}")
    public ResponseEntity<List<UserDto>> getUserInfo(@PathVariable String infoType, @PathVariable String info) {
        List<UserDto> list = userService.getUserInfo(infoType,info).values().stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
     */
