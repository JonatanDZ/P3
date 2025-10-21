package com.example.p3.controller.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "stage", schema = "dbtest")
public class Stage {
    @Id
    @Column(name = "stage_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "stage_tools",
            joinColumns = @JoinColumn(name = "stage_id", referencedColumnName = "stage_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id", referencedColumnName = "tool_id")
    )
    private Set<Tool> stageTools = new HashSet<>();
}