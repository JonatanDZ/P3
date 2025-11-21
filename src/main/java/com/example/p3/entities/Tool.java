package com.example.p3.entities;

import jakarta.persistence.*; //Enables hibernate
import jakarta.validation.constraints.Size; //This allows us to set size limitations to our attributes
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity //Used for declaring object class as an entity in the DB (Makes the hibernate possible)
@Table(name = "tool", schema = "P3")
public class Tool {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    @Column(name = "is_personal")
    private Boolean is_personal;

    @Column(name = "is_dynamic")
    private Boolean is_dynamic;

    // ManyToOne since many tools can be created by the same employee
    // the fetchtype is lazy which means it only gets the employee when explicitly asked
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "initials")
    private Employee created_by;

    // mappedBy is important here. It tells Hibernate that the User class owns this relationship. ????????????
    // Each tool can be favorited by many users.
    @ManyToMany(mappedBy = "favoriteTools", fetch = FetchType.LAZY)
    private Set<Employee> employeesWhoFavorited = new HashSet<>();



    @ManyToMany
    @JoinTable(
            name = "department_tool",                                // SELECT * FROM tool t
            joinColumns = @JoinColumn(name = "tool_id"),             //JOIN tool_department td ON t.id = td.tool_id
            inverseJoinColumns = @JoinColumn(name = "department_id") //JOIN department d ON td.department_id = d.id

    )
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

    public Tool(Integer id, String name, String url, Boolean is_personal, Boolean is_dynamic, Set<Department> departments, Set<Jurisdiction> jurisdictions, Set<Stage> stages, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.is_personal = is_personal;
        this.is_dynamic = is_dynamic;
        this.departments = departments;
        this.jurisdictions = jurisdictions;
        this.stages = stages;
        this.tags = tags;
    }

    public Tool() {}
}