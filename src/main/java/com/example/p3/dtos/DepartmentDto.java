package com.example.p3.dtos;

import com.example.p3.entities.Department;
import lombok.Data;

@Data
public class DepartmentDto {
    // attributes of Department
    private long id;
    private String name;
    private boolean isDev;

    public DepartmentDto(Department d){
        this.id = d.getId();
        this.name = d.getDepartmentName();
        this.isDev = d.getIsDev();
    }

}
