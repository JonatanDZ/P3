package com.example.p3.dtos.toolsDto;

public class CompanyToolFactory extends ToolFactory {
    @Override
    public ToolDto createTool() {
        return new CompanyToolDto();
    }
}
