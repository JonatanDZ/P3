package com.example.p3.service;


import com.example.p3.dtos.LinkDto;
import com.example.p3.model.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LinkService {

    // hashmap to be made her
    // in memory database:
    private final Map<Long, Link> inMemoryDb = new ConcurrentHashMap<>();
    private long counter = 1;
    public long useCounter() {
        return counter++;
    }

    //  Called automatically after Spring creates the service
    @PostConstruct
    public void seedData() {
        JsonParser();
    }

    // CRUD methods (Create, Read, Update, Delete)
    public Map<Long, Link> getAllLinks() {
        return inMemoryDb;
    }

    public List<LinkDto> getLinksByStage(String stage){
        List<LinkDto> list = inMemoryDb.values().stream().map(LinkDto::new).toList();
        List<LinkDto> listByStage = new ArrayList<>();
        for(int i = 0; i<inMemoryDb.size();i++){
            if(Arrays.toString(list.get(i).getStages()).contains(stage)){ //For testing use ex "DEVOPS"
                //System.out.println(Arrays.toString(list.get(i).getDepartments()));
                listByStage.add(list.get(i));
            }

           // System.out.println(list.get(i));
        }
        System.out.println(listByStage);

        return listByStage; //PLACEHOLDER
    }

    public void JsonParser() {
        ObjectMapper mapper = new ObjectMapper();
        Link[] links;

        try {
            links = mapper.readValue(new File("src/main/resources/static/MOCK_DATA.json"), Link[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Link link : links){
            inMemoryDb.put(link.getId(), link);
        }
    }
}
