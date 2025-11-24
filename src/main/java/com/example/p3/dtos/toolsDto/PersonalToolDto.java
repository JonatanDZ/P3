package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PersonalToolDto implements  ToolDto {
    private Integer id;
    private String name;
    private String url;
    private Boolean is_personal;
    private ArrayList<String> jurisdictions;
    private ArrayList<String> stage;
    private  Boolean pending;

    @Override
    public void prepare(Tool t){
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.pending = t.getPending();

        this.is_personal = t.getIs_personal(); //This is always true

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
    }
}
