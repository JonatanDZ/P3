package com.example.p3.service;

import com.example.p3.entities.Jurisdiction;

import org.springframework.stereotype.Service;
import com.example.p3.repositories.JurisdictionRepository;

import java.util.List;


@Service
public class JurisdictionService {

    private final JurisdictionRepository jurisdictionRepository;

    public JurisdictionService(JurisdictionRepository jurisdictionRepository) {
        this.jurisdictionRepository = jurisdictionRepository;
    }

    // Hent alle jurisdictions
    public List<Jurisdiction> getAllJurisdictions() {
        // findAll is a part of JpaRepository
        return jurisdictionRepository.findAll();
    }
}


