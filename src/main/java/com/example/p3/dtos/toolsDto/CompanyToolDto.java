package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Tool;

public class CompanyToolDto extends ToolDtoCLASS implements ToolInterface {

    public CompanyToolDto(Tool t) {
        super(t);
        this.differences();
    }
    @Override
    public void differences () {
        //do something about tags?
    }
}
