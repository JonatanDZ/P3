package com.example.p3.controller;


import com.example.p3.dtos.LinkDto;
import com.example.p3.service.LinkService;
import com.example.p3.model.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addlink")
    public ResponseEntity<Link> createLink(@RequestBody Link link){
        if (link==null){
            return ResponseEntity.badRequest().build();
        }

        // This is a ternary operator (just a short way to write if/else)
        String joinedtags = String.join(", ", link.getTags() != null ? link.getTags() : new String[]{});

        Link createdLink = linkService.createLink(
                null,
                link.getName(),
                link.getUrl(),
                joinedtags,
                link.getDepartments(),
                link.getStages()
        );

        return ResponseEntity.ok(createdLink);
    }

}

