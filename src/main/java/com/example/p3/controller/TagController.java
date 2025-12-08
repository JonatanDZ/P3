package com.example.p3.controller;

import com.example.p3.dtos.TagDto;

import com.example.p3.service.TagService;
import com.example.p3.entities.Tag;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Gets a list of all tags.",
            description = "Retrieves all tags in the database, given no conditions."
    )
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

    @Operation(
            summary = "Gets a single tag.",
            description = "Retrieves a single tag given the id of that tag."
    )
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

    @Operation(
            summary = "Uploads a tag to the database.",
            description = "POSTs a tag to the database given a tag as parameter (required). "
    )
    @PostMapping("")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        if (tag == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(TagService.saveTag(tag));
    }
}
