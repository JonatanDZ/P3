package com.example.p3.dtos;

import com.example.p3.model.Department;
import lombok.Data;

@Data
public class DepartmentDto {
    // attributes of Department
    private long id;
    private String name;
    private boolean isDev;

    public DepartmentDto(Department d){
        this.id = d.getId();
        this.name = d.getName();
        this.isDev = d.isDev();
    }

}
