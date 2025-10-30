package com.example.p3.service;

import com.example.p3.model.Department;
import com.example.p3.model.Jurisdiction;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JurisdictionService {
    // hashmap to be made her
    // in memory database:
    private final Map<Long, Jurisdiction> inMemoryDb = new ConcurrentHashMap<>();

    static long counter = 0;
    public long useCounter() {
        return ++counter;
    }

    @PostConstruct
    public void seedData() {
        JsonParserJurisdiction("src/main/resources/static/JURISDICTION_MOCK_DATA.json");
        // --- Mock data for development ---

    }

    public Map<Long, Jurisdiction> getAllJurisdictions() {
        System.out.println(inMemoryDb);
        return inMemoryDb;
    }

    public void JsonParserJurisdiction(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Jurisdiction[] Jurisdictions;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how tool looks
            Jurisdictions = mapper.readValue(new File(src), Jurisdiction[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for(Jurisdiction Jurisdiction : Jurisdictions){
            inMemoryDb.put(useCounter(), Jurisdiction);
        }
    }
}
