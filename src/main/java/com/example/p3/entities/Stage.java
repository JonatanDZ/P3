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
@Table(name = "stage", schema = "P3")
public class Stage {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tool_stage",
            joinColumns = @JoinColumn(name = "stage_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id")
    )
    private Set<Tool> stageTools = new HashSet<>();
}