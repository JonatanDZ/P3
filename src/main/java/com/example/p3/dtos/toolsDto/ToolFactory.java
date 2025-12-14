package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Tool;

public abstract class ToolFactory {
    //Decides which tool should be created (personal or company)
    public ToolDto determineTool(Tool tool) {
        ToolDto toolDto = createTool();
        toolDto.prepare(tool);
        return toolDto;
    }

    public abstract ToolDto createTool();
}