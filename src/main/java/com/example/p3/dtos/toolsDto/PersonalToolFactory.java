package com.example.p3.dtos.toolsDto;

public class PersonalToolFactory extends ToolFactory {
    @Override
    public ToolDto createTool(){
        return new PersonalToolDto();
    }
}
