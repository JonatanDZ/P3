package com.example.p3.service;

import com.example.p3.entities.Jurisdiction;
import com.example.p3.repositories.JurisdictionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JurisdictionService {
    private final JurisdictionRepository jurisdictionRepository;

    public JurisdictionService(JurisdictionRepository jurisdictionRepository) {
        this.jurisdictionRepository = jurisdictionRepository;
    }

    public List<Jurisdiction> getAllJurisdictions() {
        return jurisdictionRepository.findAll();
    }
}