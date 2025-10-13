package com.example.p3.service;


import com.example.p3.model.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;
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

    // CRUD methods
    public Map<Long, Link> getAllLinks() {
        return inMemoryDb;
    }

    public void JsonParser() {

        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Link[] links;

        // I needed to make a try/catch otherwise it complained.
        try {
            links = mapper.readValue(new File("src/main/resources/static/MOCK_DATA.json"), Link[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for(Link link : links){
            inMemoryDb.put(link.getId(), link);
        }
    }

    public List<Link> findByJurisdiction(Link.Jurisdiction jurisdiction) {
        return inMemoryDb.values().stream()
                .filter(l -> {
                    var arr =  l.getJurisdictions();
                    return arr != null && Arrays.asList(arr).contains(jurisdiction);
                })
                .toList();
    }
}
