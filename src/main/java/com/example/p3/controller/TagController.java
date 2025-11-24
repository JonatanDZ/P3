package com.example.p3.controller;

import com.example.p3.dtos.TagDto;

import com.example.p3.entities.Employee;
import com.example.p3.service.TagService;
import com.example.p3.entities.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService TagService;
    private final TagService tagService;

    public TagController(TagService TagService, TagService tagService) {
        this.TagService = TagService;
        this.tagService = tagService;
    }

    @GetMapping("")
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> list = TagService.getAllTags().stream()
                .map(TagDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable int id) {
        Tag tag = tagService.getTagById(id).orElse(null);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new TagDto(tag));
        }
    }

    @PostMapping("")
    public ResponseEntity<Tag> createTool(@RequestBody Tag tag){
        if (tag == null){
            return ResponseEntity.badRequest().build();
        }
        Tag savedTag = tagService.saveTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
    }


}
