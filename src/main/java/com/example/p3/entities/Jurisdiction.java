package com.example.p3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// This entity is an ORM-mapped representation of a Jurisdiction in the system. It maps to the P3.jurisdiction table and contains the jurisdiction's attributes/rows from the DB.
@Getter
@Setter
@Entity
@Table(name = "jurisdiction", schema = "P3")
public class Jurisdiction {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    // starting at a Jurisdiction, Hibernate looks in tool_jurisdiction for rows whose jurisdiction_id equals this jurisdiction’s id,
    // then uses each tool_id it finds to load the corresponding Tool rows.
    // No department is involved in this relationship.
    @ManyToMany(mappedBy = "jurisdictions")
    private Set<Tool> jurisdictionTools = new HashSet<>();

}