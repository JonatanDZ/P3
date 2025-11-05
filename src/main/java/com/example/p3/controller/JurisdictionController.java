package com.example.p3.controller;

import com.example.p3.dtos.JurisdictionDto;
import com.example.p3.service.JurisdictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jurisdictions")
public class JurisdictionController {
    private final JurisdictionService JurisdictionService;

    public JurisdictionController(JurisdictionService JurisdictionService) {
        this.JurisdictionService = JurisdictionService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JurisdictionDto>> getAllJurisdictions(){
        List<JurisdictionDto> list = JurisdictionService.getAllJurisdictions().stream()
                .map(JurisdictionDto::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<JurisdictionDto> getJurisdictionsByName(@PathVariable String name){
        return JurisdictionService.getByName(name)
                .map(JurisdictionDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());    }
}
