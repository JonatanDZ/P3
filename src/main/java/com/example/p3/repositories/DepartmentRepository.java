package com.example.p3.repositories;

import com.example.p3.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

// This looks empty, but it implements findAll() that is a function in JpaRepository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}