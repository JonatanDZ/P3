package com.example.p3.controller;


import com.example.p3.dtos.LinkDto;
import com.example.p3.model.Link;
import com.example.p3.service.LinkService;
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


    @PostMapping("/getLinksByStage/{stage}")
    public ResponseEntity<List<LinkDto>> getLinksByStage(@PathVariable("stage") String stage){
        List<LinkDto> list = linkService.getLinksByStage(stage);
        return ResponseEntity.ok(list);
//CODE HERE YES :)

    }
}
