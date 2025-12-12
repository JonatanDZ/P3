package com.example.p3.service;

import com.example.p3.entities.Department;
import com.example.p3.repositories.DepartmentRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        // findAll is a part of JpaRepository
        return departmentRepository.findAll();
    }
}