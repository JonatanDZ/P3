package com.example.p3.dtos;

import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    //Hvor får den sin data fra/ hvordan ved den hvilke juridictions der skal være i dens array ?????????????
    //Takes the entities from the database and converts it into objects of the type tool
    public ToolDto(Tool t) {
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.isDynamic = t.getIsDynamic();
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