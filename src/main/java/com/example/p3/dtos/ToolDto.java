package com.example.p3.dtos;

import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
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
    private ArrayList<String> departments;
    private ArrayList<String> jurisdictions;
    private ArrayList<String> stage;

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
        String departmentName;
        List<Department> departmentList = t.getDepartments().stream().toList();
        for (int i = 0; i < departmentList.size(); i++) {
            departmentName = departmentList.get(i).getDepartmentName();
            this.departments.add(departmentName);
        }

        this.jurisdictions = new ArrayList<>();
        String jurisdictionName;
        List<Jurisdiction> jurisdictionList = t.getJurisdictions().stream().toList();
        for (int i = 0; i < jurisdictionList.size(); i++) {
            jurisdictionName = jurisdictionList.get(i).getJurisdictionName();
            this.jurisdictions.add(jurisdictionName);
        }

        this.stage = new ArrayList<>();
        String stageName;
        List<Stage> stageList = t.getStages().stream().toList();
        for (int i = 0; i < stageList.size(); i++) {
            stageName = stageList.get(i).getName();
            this.stage.add(stageName);
        }

    }
}