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
@Table(name = "users", schema = "dbtest")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "initials")
    private String initials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    // implementing the favorite_tools table.
    // User.java
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_tools",
            // the leftmost user_id is the column in the users table, and the rightmost is the id of favorite_tools
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            // inverse sounds way more complex than it is. There are two foreign keys in the favorite_tools table, therefore there are two JOIN statements.
            inverseJoinColumns = @JoinColumn(name = "tool_id", referencedColumnName = "tool_id")
    )
    private Set<Tool> favoriteTools = new HashSet<>();

    public void addFavorite(Tool tool) {
        favoriteTools.add(tool);
        tool.getUsersWhoFavorited().add(this); // keep both sides in sync
    }

    public void removeFavorite(Tool tool) {
        favoriteTools.remove(tool);
        tool.getUsersWhoFavorited().remove(this);
    }
}