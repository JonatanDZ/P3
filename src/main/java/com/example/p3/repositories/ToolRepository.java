package com.example.p3.repositories;

import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
}