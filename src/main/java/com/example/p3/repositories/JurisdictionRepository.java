package com.example.p3.repositories;

import com.example.p3.entities.Jurisdiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JurisdictionRepository extends JpaRepository<Jurisdiction, Integer> {
}