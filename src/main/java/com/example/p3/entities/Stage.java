package com.example.p3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// This entity is an ORM-mapped representation of a Stage in the system. It maps to the P3.stage table and contains the stage's attributes/rows from the DB.
@Getter
@Setter
@Entity
@Table(name = "stage", schema = "P3")
public class Stage {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "stages")
    private Set<Tool> stageTools = new HashSet<>();
}