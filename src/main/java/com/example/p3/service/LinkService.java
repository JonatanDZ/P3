package com.example.p3.service;


import com.example.p3.dtos.LinkDto;
import com.example.p3.model.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class LinkService {

    // hashmap to be made her
    // in memory database:
    private final Map<Long, Link> inMemoryDb = new ConcurrentHashMap<>();
    //  Called automatically after Spring creates the service
    @PostConstruct
    public void seedData() {
        JsonParser("src/main/resources/static/MOCK_DATA.json");
        // --- Mock data for development ---
    }


    // CRUD methods (Create, Read, Update, Delete)

    // Min forståelse af isDynamic er den skal gemme på "brugerens" navn
    // Here we define how to create a link (The first string is for ID)
    public Link createLink(String spaceId,
                                     String name,
                                     String url,
                                     String tags,
                                     Link.Department[] department,
                                     Link.Stage[] stages,
                                     boolean isDynamic)
    {
        Link createdlink = new Link();
        createdlink.setId(useCounter());
        createdlink.setName(name);
        createdlink.setUrl(url);
        createdlink.setTags(tags.split(",")); // [.... , ..... , .... , ..]
        createdlink.setDepartments(department);
        createdlink.setStages(stages);
        createdlink.setDynamic(isDynamic);

        // Store the object createdlink in the Hash map (inMemoryDb), and use its ID (createdlink.getId()) as the key.
        inMemoryDb.put(createdlink.getId(), createdlink);
        return createdlink;
    }
    // CRUD methods

    public Map<Long, Link> getAllLinks() {
        return inMemoryDb;
    }

    public List<LinkDto> getLinksByStage(String stage){
        List<LinkDto> list = inMemoryDb.values().stream().map(LinkDto::new).toList();
        List<LinkDto> listByStage = new ArrayList<>();
        for(int i = 0; i<inMemoryDb.size();i++){
            if(Arrays.toString(list.get(i).getStages()).contains(stage)){
                listByStage.add(list.get(i));
            }
        }
        //System.out.println(listByStage);

        return listByStage;
    }

    public List<Link> findByJurisdiction(Link.Jurisdiction jurisdiction) {
        return inMemoryDb.values().stream()
                .filter(l -> {
                    var arr =  l.getJurisdictions();
                    return arr != null && Arrays.asList(arr).contains(jurisdiction);
                })
                .toList();
    }

    public void JsonParser(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Link[] links;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how Link looks
            links = mapper.readValue(new File(src), Link[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for(Link link : links){
            inMemoryDb.put(link.getId(), link);
        }
    }

    //Filters the links so only link with the department from the URL is returned
    public Map<Long, Link> getAllLinksByDepartment(Link.Department department) {
        return getAllLinks().entrySet().stream()
            .filter(entry -> Arrays.asList(entry.getValue().getDepartments()).contains(department))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Link> getAllLinksByDepartmentJurisdictionStage(
            Link.Department department,
            Link.Jurisdiction jurisdiction,
            Link.Stage stage
    ) {
        return getAllLinks().entrySet().stream()
                .filter(entry -> Arrays.asList(entry.getValue().getDepartments()).contains(department))
                .filter(entry -> Arrays.asList(entry.getValue().getJurisdictions()).contains(jurisdiction))
                .filter(entry -> Arrays.asList(entry.getValue().getStages()).contains(stage))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}


// Test
// {
//  "name": "Github",
//  "url": "https://github.com",
//  "tags": ["code", "repository"],
//  "departments": ["Development"],
//  "stages": ["Production"],
//  "isDynamic": true
//}