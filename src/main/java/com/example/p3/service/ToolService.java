package com.example.p3.service;


import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;

    public List<com.example.p3.entities.Tool> getAllTools() {
        return toolRepository.findAll();
    }

    //Filters the tools so only tool with the department from the URL is returned
    public List<com.example.p3.entities.Tool> getAllToolsByDepartment(Department department) {
        return getAllTools().stream()
                .filter(tool -> tool.getDepartments().contains(department))
                .collect(Collectors.toList());
    }

    public List<com.example.p3.entities.Tool> getAllToolsByJurisdiction(Jurisdiction jurisdiction) {
        return getAllTools().stream()
                .filter(tool -> tool.getJurisdictions().contains(jurisdiction))
                .collect(Collectors.toList());
    }

    public List<com.example.p3.entities.Tool> getAllToolsByStage(Stage stage) {
        return getAllTools().stream()
                .filter(tool -> tool.getStages().contains(stage))
                .collect(Collectors.toList());
    }

    public List<com.example.p3.entities.Tool> getAllToolsByDepartmentJurisdictionStage(
            Department department,
            Jurisdiction jurisdiction,
            Stage stage
    ) {
        return getAllTools().stream()
                .filter(tool -> tool.getDepartments().contains(department))
                .filter(tool -> tool.getJurisdictions().contains(jurisdiction))
                .filter(tool -> tool.getStages().contains(stage))
                .collect(Collectors.toList());
    }

    // hashmap to be made her
    // in memory database:
//    private final Map<Long, Tool> inMemoryDb = new ConcurrentHashMap<>();
//
//    static long counter = 0;
//
//    public long useCounter() {
//        return ++counter;
//    }

    //  Called automatically after Spring creates the service
   /* @PostConstruct
    public void seedData() {
        JsonParserTool("src/main/resources/static/TOOL_MOCK_DATA.json");
        // --- Mock data for development ---
    }*/


    // CRUD methods (Create, Read, Update, Delete)

    // Min forståelse af isDynamic er den skal gemme på "brugerens" navn
    // Here we define how to create a tool (The first string is for ID)



//    public Tool createTool(String spaceId,
//                           String name,
//                           String url,
//                           String tags,
//                           Tool.Department[] departments,
//                           Tool.Stage[] stages,
//                           Tool.Jurisdiction[] jurisdictions,
//                           boolean isDynamic) {
//        Tool createdtool = new Tool();
//        createdtool.setId(useCounter());
//        createdtool.setName(name);
//        createdtool.setUrl(url);
//        createdtool.setTags(tags.split(",")); // [.... , ..... , .... , ..]
//        createdtool.setDepartments(departments);
//        createdtool.setStages(stages);
//        createdtool.setJurisdictions(jurisdictions);
//        createdtool.setDynamic(isDynamic);
//
//        // Store the object createdtool in the Hash map (inMemoryDb), and use its ID (createdtool.getId()) as the key.
//        inMemoryDb.put(createdtool.getId(), createdtool);
//        return createdtool;
//    }


//    //Makes model tool from entity tool
//    private Tool toModel(com.example.p3.entities.Tool e) {
//        Tool m = new Tool();
//        m.setId(e.getId());
//        m.setName(e.getName());
//        m.setUrl(e.getUrl());
//        m.setDynamic(e.getIsDynamic());
//        //m.setDepartments(e.getDepartments());
//        //m.setStages(e.getStages());
//        //m.setJurisdictions(e.getJurisdictions());
//        return m;
//    }
}



   /* public void JsonParserTool(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Tool[] tools;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how tool looks
            tools = mapper.readValue(new File(src), Tool[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for (Tool tool : tools) {
            inMemoryDb.put(tool.getId(), tool);
        }
    }
}*/







// Test
// {
//  "name": "Github",
//  "url": "https://github.com",
//  "tags": ["code", "repository"],
//  "departments": ["Development"],
//  "stages": ["Production"],
//  "isDynamic": true
//}