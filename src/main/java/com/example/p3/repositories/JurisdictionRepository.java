package com.example.p3.repositories;

import com.example.p3.entities.Jurisdiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JurisdictionRepository extends JpaRepository<Jurisdiction, Integer> {
    Optional<Jurisdiction> findByJurisdictionName(String name);
}