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
import java.util.stream.Collectors;

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
        JsonParser("src/main/resources/static/MOCK_DATA.json");
    }

    // CRUD methods
    public Map<Long, Link> getAllLinks() {
        return inMemoryDb;
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
}
