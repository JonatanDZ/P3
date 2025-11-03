package com.example.p3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "jurisdiction", schema = "P3")
public class Jurisdiction {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String jurisdictionName;

    // starting at a Jurisdiction, Hibernate looks in tool_jurisdiction for rows whose jurisdiction_id equals this jurisdiction’s id,
    // then uses each tool_id it finds to load the corresponding Tool rows.
    // No department is involved in this relationship.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tool_jurisdiction",
            joinColumns = @JoinColumn(name = "jurisdiction_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id")
    )
    private Set<Tool> jurisdictionTools = new HashSet<>();

}