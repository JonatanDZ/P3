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
@Table(name = "employee", schema = "P3")
public class Employee {
    @Id
    @Column(name = "initials", nullable = false)
    private String initials;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    // implementing the favorite_tools table.
    // employee.java
    // the leftmost employee_id is the column in the employees table, and the rightmost is the id of favorite_tools
    // inverse sounds way more complex than it is. There are two foreign keys in the favorite_tools table, therefore there are two JOIN statements.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite_tool",
            joinColumns = @JoinColumn(name = "employee_initials", referencedColumnName = "initials"),
            inverseJoinColumns = @JoinColumn(name = "tool_id")
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

    public Employee() {

    }

    public Employee(String initials, String name, String email) {
        this.initials = initials;
        this.name = name;
        this.email = email;
    }

    public Employee(){}
}