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
@Table(name = "employees", schema = "P3")
public class Employee {
    @Id
    @Column(name = "employee_id", nullable = false)
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
    private Boolean isAdmin; //Det var vel ikke sådan, at det skal håndteres????

    // implementing the favorite_tools table.
    // employee.java
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_tools",
            // the leftmost employee_id is the column in the employees table, and the rightmost is the id of favorite_tools
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employee_id"),
            // inverse sounds way more complex than it is. There are two foreign keys in the favorite_tools table, therefore there are two JOIN statements.
            inverseJoinColumns = @JoinColumn(name = "tool_id", referencedColumnName = "tool_id")
    )
    private Set<Tool> favoriteTools = new HashSet<>();

    public void addFavorite(Tool tool) {
        favoriteTools.add(tool);
        tool.getEmployeesWhoFavorited().add(this); // keep both sides in sync
    }

    public void removeFavorite(Tool tool) {
        favoriteTools.remove(tool);
        tool.getEmployeesWhoFavorited().remove(this);
    }
}