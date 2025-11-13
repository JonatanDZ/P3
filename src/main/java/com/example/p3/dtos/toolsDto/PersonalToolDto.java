package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Tool;

public class PersonalToolDto extends ToolDtoCLASS implements ToolInterface {

    public PersonalToolDto(Tool t) {
        super(t);
        this.differences();
    }

    @Override
    public void differences () {}
}
