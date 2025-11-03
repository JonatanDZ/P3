package com.example.p3.dtos;

import java.util.ArrayList;
import java.util.List;

import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Tool;
import lombok.Data;

@Data
public class JurisdictionDto {
    // attributes of Jurisdiction
    private long id;
    private String name;
    private ArrayList<Integer> tools;

    public JurisdictionDto(Jurisdiction j){
        this.id = j.getId();
        this.name = j.getJurisdictionName();
        this.tools = new ArrayList<>();
        int toolID;
        List<Tool> toolList = j.getJurisdictionTools().stream().toList();
        for (int i = 0; i < toolList.size(); i++) {
            toolID = toolList.get(i).getId();
            this.tools.add(toolID);
        }
    }
}


