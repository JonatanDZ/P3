package com.example.p3.controller;

import com.example.p3.dtos.TagDto;

import com.example.p3.entities.Employee;
import com.example.p3.service.TagService;
import com.example.p3.entities.Tag;
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
        // Calls tag service to get all tags
        List<TagDto> list = TagService.getAllTags()
                // Turns it into a stream to be processed with the streams API
                .stream()
                // For each tag in the stream, a new TagDto is created
                .map(TagDto::new)
                // Collects the mapped stream and puts it into List<TagDto>
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable int id) {
        // Calls the tagService to find the tag by Id
        Tag tag = tagService.getTagById(id).orElse(null);

        // If the tagId provided in the pathvariable is not existing it will return Not Found
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
        return ResponseEntity.ok(TagService.saveTag(tag));
    }


}
