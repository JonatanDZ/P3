package com.example.p3.controller;

import com.example.p3.dtos.JurisdictionDto;
import com.example.p3.service.JurisdictionService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// Makes "/jurisdictions" always being a part of the endpoint call
@RequestMapping("/jurisdictions")
public class JurisdictionController {
    private final JurisdictionService JurisdictionService;

    // TODO: We should remove this and use lombok like every other controller.
    // The JurisdictionService constructor.
    public JurisdictionController(JurisdictionService JurisdictionService) {
        this.JurisdictionService = JurisdictionService;
    }

    //Swagger annotation
    @Operation(
            summary = "Gets all jurisdictions.",
            description = "Returns a list of all jurisdictions in the db given no conditions."
    )

    // The endpoint is empty and will run if "/jurisdictions" are called
    @GetMapping("")
    public ResponseEntity<List<JurisdictionDto>> getAllJurisdictions(){
        // Calls service to get all jurisdictions
        // Fetch all Jurisdiction entities and convert them to DTOs
        List<JurisdictionDto> list = JurisdictionService.getAllJurisdictions()
                // Converts the list returned by the service into a stream so we can use the Streams API (map, filter, etc.).
                .stream()
                // For each jurisdiction(jurisdiction entity) in the stream, a new jurisdictionDto is created with the jurisdictionDto constructor.
                .map(JurisdictionDto::new)
                // Collects the mapped stream and puts it into List<JurisdictionDto>
                .toList();
        // Returns a list of jurisdictionDTOs wrapped in a ResponseEntity to control HTTP status, headers, etc.
        return ResponseEntity.ok(list);
    }
}
