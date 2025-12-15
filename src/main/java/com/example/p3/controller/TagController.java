package com.example.p3.controller;

import com.example.p3.dtos.TagDto;
import com.example.p3.service.TagService;
import com.example.p3.entities.Tag;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Makes "/tags" always being a part of the endpoint call
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    // TODO: Use lombok
    public TagController( TagService tagService) {
        this.tagService = tagService;
    }

    //Swagger annotation
    @Operation(
            summary = "Gets a list of all tags.",
            description = "Retrieves all tags in the database, given no conditions."
    )
    @GetMapping("")
    public ResponseEntity<List<TagDto>> getAllTags() {
        // Calls tag service to get all tags
        // Fetch all tag entities and convert them to DTOs
        List<TagDto> list = tagService.getAllTags()
                // Converts the list returned by the service into a stream so we can use the Streams API (map, filter, etc.).
                .stream()
                // For each tag(tag entity) in the stream, a new TagDto is created with the TagDto constructor.
                .map(TagDto::new)
                // Collects the mapped stream and puts it into List<TagDto>
                .toList();
        // Returns a list of TagDTOs wrapped in a ResponseEntity to control HTTP status, headers, etc.
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

        // If the tagId provided in the path variable is not existing it will return Not Found
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
        return ResponseEntity.ok(tagService.saveTag(tag));
    }
}
