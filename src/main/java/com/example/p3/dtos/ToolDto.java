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
    private ArrayList<Integer> departments;
    private ArrayList<Integer> jurisdictions;
    private ArrayList<Integer> stage;

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
        List<Department> departmentList = t.getDepartments().stream().toList();
        for (int i = 0; i < departmentList.size(); i++) {
            departmentID = departmentList.get(i).getId();
            System.out.println(departmentID);
            this.departments.add(departmentID);
        }

        this.jurisdictions = new ArrayList<>();
        int jurisdictionID;
        List<Jurisdiction> jurisdictionList = t.getJurisdictions().stream().toList();
        for (int i = 0; i < jurisdictionList.size(); i++) {
            jurisdictionID = jurisdictionList.get(i).getId();
            System.out.println(jurisdictionID);
            this.jurisdictions.add(jurisdictionID);
        }



        this.stage = new ArrayList<>();
        int stageID;
        List<Stage> stageList = t.getStages().stream().toList();
        for (int i = 0; i < stageList.size(); i++) {
            stageID = stageList.get(i).getId();
            System.out.println(stageID);
            this.stage.add(stageID);
        }

    }
}
