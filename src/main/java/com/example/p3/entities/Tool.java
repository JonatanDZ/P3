package com.example.p3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tool", schema = "P3")
public class Tool {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    @Column(name = "is_personal")
    private Boolean isPersonal;

    @Column(name = "is_dynamic")
    private Boolean isDynamic;

    // mappedBy is important here. It tells Hibernate that the User class owns this relationship.
    // Each tool can be favorited by many users.
    @ManyToMany(mappedBy = "favoriteTools", fetch = FetchType.LAZY)
    private Set<Employee> employeesWhoFavorited = new HashSet<>();

    @ManyToMany(mappedBy = "departmentTools")
    private Set<Department> departments;

    @ManyToMany
    @JoinTable(
            name = "tool_jurisdiction",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "jurisdiction_id")
    )
    private Set<Jurisdiction> jurisdictions;

    @ManyToMany
    @JoinTable(
            name = "tool_stage",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "stage_id")
    )
    private Set<Stage> stages;

    @ManyToMany
    @JoinTable(
            name = "tool_tag",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Tool(Integer id, String name, String url, Boolean isPersonal, Boolean isDynamic, Set<Department> departments, Set<Jurisdiction> jurisdictions, Set<Stage> stages, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.isPersonal = isPersonal;
        this.isDynamic = isDynamic;
        this.departments = departments;
        this.jurisdictions = jurisdictions;
        this.stages = stages;
        this.tags = tags;
    }

    public Tool() {}
}