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
    @Column(name = "jurisdiction_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "jurisdiction_name")
    private String jurisdictionName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jurisdiction_tools",
            joinColumns = @JoinColumn(name = "jurisdiction_id", referencedColumnName = "jurisdiction_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id", referencedColumnName = "tool_id")
    )
    private Set<Tool> jurisdictionTools = new HashSet<>();

}