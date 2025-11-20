package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CompanyToolDto implements ToolDto {

    private Integer id;
    private String name;
    private String url;
    private Boolean is_dynamic;
    private String created_by;
    private ArrayList<String> departments;
    private ArrayList<String> jurisdictions;
    private ArrayList<String> stage;
    private ArrayList<String> tags;

    @Override
    public void prepare(Tool t){
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.is_dynamic = t.getIs_dynamic();
        // gets an employee object, then we use the getter from Employee
        // this checks if the employee object got is null, if true the row should be null, else it should get the initials
        // if this is not present it will create issues in displaying tools and destroy the UI !!!
        this.created_by = t.getCreated_by() == null ? null : t.getCreated_by().getInitials();

        this.departments = new ArrayList<>();
        String departmentName;
        List<Department> departmentList = t.getDepartments().stream().toList();
        for (int i = 0; i < departmentList.size(); i++) {
            departmentName = departmentList.get(i).getName();
            this.departments.add(departmentName);
        }

        this.jurisdictions = new ArrayList<>();
        String jurisdictionName;
        List<Jurisdiction> jurisdictionList = t.getJurisdictions().stream().toList();
        for (int i = 0; i < jurisdictionList.size(); i++) {
            jurisdictionName = jurisdictionList.get(i).getName();
            this.jurisdictions.add(jurisdictionName);
        }

        this.stage = new ArrayList<>();
        String stageName;
        List<Stage> stageList = t.getStages().stream().toList();
        for (int i = 0; i < stageList.size(); i++) {
            stageName = stageList.get(i).getName();
            this.stage.add(stageName);
        }

        /*this.tags = new ArrayList<>();
        String tagName;
        List<Tags> tagsList = t.getTags().stream().toList();
        for (int i = 0; i < tagsList.size(); i++) {
            tagName = tagsList.get(i).getName();
            this.stage.add(tagName);
        }*/
    }
}
