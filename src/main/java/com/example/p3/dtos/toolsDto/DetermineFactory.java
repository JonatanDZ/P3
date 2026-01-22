package com.example.p3.dtos.toolsDto;

import com.example.p3.entities.Tool;

import java.util.HashMap;
import java.util.Map;

public class DetermineFactory {
    //Hash map to store our different toolTypes and connected factories in
    private final Map<String, ToolFactory> factoryRegistry = new HashMap<>();

    public DetermineFactory() {
        // set our different toolTypes and connected factories in the constructor
        factoryRegistry.put("PERSONAL", new PersonalToolFactory());
        factoryRegistry.put("COMPANY", new CompanyToolFactory());
        //factoryRegistry.put("ADDITIONAL", new AdditionalToolFactory());
        //factoryRegistry.put("ANOTHER", new AnotherToolFactory());

    }

    public ToolDto decideFactory(Tool t) {
        //Returns a key to factoryRegistry
        String toolType = determineToolType(t);

        //Returns the factory that matches the string.
        ToolFactory factory = factoryRegistry.get(toolType);

        return factory.determineTool(t);
    }

    private String determineToolType(Tool t) {
        return t.getIs_personal()  ? "PERSONAL" : "COMPANY";
    }
}

