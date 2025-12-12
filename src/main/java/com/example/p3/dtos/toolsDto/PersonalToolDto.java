package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PersonalToolDto implements  ToolDto {
    private Integer id;
    private Boolean is_personal = true;
    private String name;
    private String url;
    private List<String> jurisdictions;
    private List<String> stage;
    private  Boolean pending;

    @Override
    public void prepare(Tool t){
        this.id = t.getId();
        this.name = t.getName();
        this.url = t.getUrl();
        this.pending = t.getPending();

        this.jurisdictions = t.getJurisdictions()
                .stream()
                .map(Jurisdiction::getName)
                .collect(Collectors.toList());

        this.stage = t.getStages()
                .stream()
                .map(Stage::getName)
                .collect(Collectors.toList());
    }
}
