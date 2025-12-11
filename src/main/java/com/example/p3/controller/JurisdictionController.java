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
        List<JurisdictionDto> list = JurisdictionService.getAllJurisdictions()
                // Turns it into a stream to be processed with the streams API
                .stream()
                // For each jurisdiction in the stream, a new JurisdictionDto is created
                .map(JurisdictionDto::new)
                // Collects the mapped stream and puts it into List<JurisdictionDto>
                .toList();
        return ResponseEntity.ok(list);
    }

}
