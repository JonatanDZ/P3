package com.example.p3.service;

import com.example.p3.entities.Jurisdiction;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.example.p3.repositories.JurisdictionRepository;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class JurisdictionService {

    private final JurisdictionRepository jurisdictionRepository;

    public JurisdictionService(JurisdictionRepository jurisdictionRepository) {
        this.jurisdictionRepository = jurisdictionRepository;
    }

    // Hent alle jurisdictions
    public List<Jurisdiction> getAllJurisdictions() {
        return jurisdictionRepository.findAll();
    }

    public Optional<Jurisdiction> getByName(String name) {
        return jurisdictionRepository.findByName(name);
    }
}


