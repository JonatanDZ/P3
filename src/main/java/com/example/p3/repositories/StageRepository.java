package com.example.p3.repositories;

import com.example.p3.entities.Stage;

import org.springframework.data.jpa.repository.JpaRepository;

// only used in testing. It is relevant to do so in order to create mock stage.
public interface StageRepository extends JpaRepository<Stage, Integer> {}