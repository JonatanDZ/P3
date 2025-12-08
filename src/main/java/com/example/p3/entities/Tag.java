package com.example.p3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// This entity is an ORM-mapped representation of a Tag in the system. It maps to the P3.tag table and contains the tag's attributes/rows from the DB.
@Getter
@Setter
@Entity
@Table(name = "tag", schema = "P3")
public class Tag {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "value", nullable = false)
    private String value;


    @ManyToMany(mappedBy = "tags")
    private Set<Tool> tagTools = new HashSet<>();

}