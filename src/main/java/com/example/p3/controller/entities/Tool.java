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
@Table(name = "tool", schema = "dbtest")
public class Tool {
    @Id
    @Column(name = "tool_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    @Column(name = "is_dynamic")
    private Boolean isDynamic;

    // mappedBy is important here. It tells Hibernate that the User class owns this relationship.
    // Each tool can be favorited by many users.
    @ManyToMany(mappedBy = "favoriteTools", fetch = FetchType.LAZY)
    private Set<User> usersWhoFavorited = new HashSet<>();

    @ManyToMany(mappedBy = "tools")
    private Set<Department> departments;

    @ManyToMany(mappedBy = "tools")
    private Set<Jurisdiction> jurisdictions;

    @ManyToMany(mappedBy = "tools")
    private Set<Stage> stages;
}