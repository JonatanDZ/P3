package com.example.p3.controller;

import com.example.p3.dtos.TagDto;

import com.example.p3.service.TagService;
import com.example.p3.entities.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService TagService;

    public TagController(TagService TagService) {
        this.TagService = TagService;
    }

    @GetMapping("")
    public ResponseEntity<List<TagDto>> getAllJurisdictions() {
        List<TagDto> list = TagService.getAllTags().stream()
                .map(TagDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("")
    public ResponseEntity<Tag> createTool(@RequestBody Tag tag){
        if (tag == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(TagService.saveTag(tag));
    }


}
