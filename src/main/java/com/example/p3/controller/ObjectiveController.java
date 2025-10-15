package com.example.p3.controller;


import com.example.p3.dtos.LinkDto;
import com.example.p3.model.Link;
import com.example.p3.service.LinkService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// This is the API http/rest controller
@RestController
@RequestMapping()
public class ObjectiveController {
    private final LinkService linkService;

    public ObjectiveController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/getLinks")
    public ResponseEntity<List<LinkDto>> getAllLinks(){
        List<LinkDto> list = linkService.getAllLinks().values().stream()
                .map(LinkDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/{jurisdiction}/getLinks")
    public ResponseEntity<List<LinkDto>> getByJurisdiction(@PathVariable Link.Jurisdiction jurisdiction) {
        List<LinkDto> list = linkService.findByJurisdiction(jurisdiction).stream()
                .map(LinkDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Call "getAllLinksByDepartment" which sort the links according to the department in the URL
    @GetMapping("/getLinks/{department}")
    public ResponseEntity<List<LinkDto>> getAllLinksByDepartment(@PathVariable Link.Department department) {
        List<LinkDto> list = linkService.getAllLinksByDepartment(department).values().stream()
                .map(LinkDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addlink")
    public ResponseEntity<Link> createLink(@RequestBody Link link){
        if (link==null){
            return ResponseEntity.badRequest().build();
        }


        Link createdLink = linkService.createLink(
                null,
                link.getName(),
                link.getUrl(),
                link.tagsToString(),
                link.getDepartments(),
                link.getStages(),
                link.isDynamic(),
                link.getCreatedBy()
        );

        return ResponseEntity.ok(createdLink);
    }

}

