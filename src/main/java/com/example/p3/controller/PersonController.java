package com.example.p3.controller;

import com.example.p3.dtos.person.PersonDto;
import com.example.p3.model.person.Person;
import com.example.p3.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/getPersons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //Default
    @GetMapping()
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<PersonDto> list = personService.getAllPersons().values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<PersonDto>> getAllPersonsByDepartment(@PathVariable Person.Department department) {
        List<PersonDto> list = personService.getAllPersonsByDepartment(department).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getpersonById" which selects the person according to the id (skal være initialer) in the URL
    @GetMapping("/id/{id}")
    public ResponseEntity<List<PersonDto>> getPersonById(@PathVariable long id) {
        List<PersonDto> list = personService.getPersonById(id).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/initials/{initials}")
    public ResponseEntity<List<PersonDto>> getPersonByInitials(@PathVariable String initials) {
        List<PersonDto> list = personService.getPersonByInitials(initials).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<PersonDto>> getPersonByEmail(@PathVariable String email) {
        List<PersonDto> list = personService.getPersonByEmail(email).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<PersonDto>> getPersonByName(@PathVariable String name) {
        List<PersonDto> list = personService.getPersonByName(name).values().stream()
                .map(PersonDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}


    /* Potentiel samlet function?
    @GetMapping("/{infoType}/{info}")
    public ResponseEntity<List<personDto>> getPersonInfo(@PathVariable String infoType, @PathVariable String info) {
        List<personDto> list = personService.getPersonInfo(infoType,info).values().stream()
                .map(personDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
     */
