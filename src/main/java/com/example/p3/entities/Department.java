package com.example.p3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// This entity is an ORM-mapped representation of a department in the system. It maps to the P3.department table and contains the department's attributes/rows from the DB.
@Getter
@Setter
@Entity
@Table(name = "department", schema = "P3")
public class Department {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Column(name = "is_dev")
    private Boolean is_dev;

    @ManyToMany(mappedBy = "departments")
    private Set<Tool> departmentTools = new HashSet<>();

    //This is constructor only used for test
    public Department(Integer id, String departmentName, Boolean isDev) {
        this.id = id;
        this.name = departmentName;
        this.is_dev = isDev;
    }

    public Department() {}
}