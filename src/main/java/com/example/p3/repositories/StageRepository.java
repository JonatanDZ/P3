package com.example.p3.repositories;

import com.example.p3.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

// only used in testing. It is relevant to do so in order to create mock stages.
public interface StageRepository extends JpaRepository<Stage, Integer> {
}