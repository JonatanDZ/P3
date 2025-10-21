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
@Table(name = "department", schema = "dbtest")
public class Department {
    @Id
    @Column(name = "department_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "is_dev")
    private Boolean isDev;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "department_tool",
            joinColumns = @JoinColumn(name = "department", referencedColumnName = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id", referencedColumnName = "tool_id")
    )
    private Set<Tool> departmentTools = new HashSet<>();

}