package com.example.p3.service;


import com.example.p3.dtos.LinkDto;
import com.example.p3.model.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    public Map<Long, Link> getLinksByStage(){
        List<LinkDto> list = inMemoryDb.values().stream().map(LinkDto::new).toList();
        for(int i = 0; i<inMemoryDb.size();i++){
            if(Arrays.toString(list.get(i).getDepartments()).contains("DEVOPS")){
                System.out.println(Arrays.toString(list.get(i).getDepartments()));
            }

           // System.out.println(list.get(i));
        }

        return inMemoryDb; //PLACEHOLDER
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
