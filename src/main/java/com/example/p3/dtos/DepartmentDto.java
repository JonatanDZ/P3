package com.example.p3.dtos;

import com.example.p3.entities.Department;

import lombok.Data;

@Data
public class DepartmentDto {
    // attributes of Department
    private long id;
    private String name;
    private boolean is_dev;

    public DepartmentDto(Department d){
        this.id = d.getId();
        this.name = d.getName();
        this.is_dev = d.getIs_dev();
    }
}