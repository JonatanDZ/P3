package com.example.p3.dtos;

import com.example.p3.entities.Department;
import com.example.p3.entities.Tool;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// data transfer objects, to JSON
@Data
public class ToolDto {
    // attributes of Tool
    private Integer id;
    private String name;
    private String url;
    private Boolean isDynamic;
    //private Set<Department> departments;
    private ArrayList<Integer> departments;

    /*private String[] tags;
    // there can be multiple departments to a Tool
    private Tool.Department[] departments;

    // there can be multiple stages to a Tool
    private Tool.Stage[] stages;

    private Tool.Jurisdiction[] jurisdictions;*/

    public ToolDto(Tool t) {
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.isDynamic = t.getIsDynamic();
        //this.tags = t.getTags();
        this.departments = new ArrayList<>();
        int departmentID;
        List<Department> departmenList = t.getDepartments().stream().toList();
        for (int i = 0; i < departmenList.size(); i++) {
            departmentID = departmenList.get(i).getId();
            System.out.println(departmentID);
            this.departments.add(departmentID);
        }
        //this.departments = t.getDepartments();
        //this.stages = t.getStages();
        //this.jurisdictions = t.getJurisdictions();
    }
}
