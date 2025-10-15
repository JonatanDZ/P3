package com.example.p3.service;

import com.example.p3.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<Long, User> inMemoryDb = new ConcurrentHashMap<>();

    static long counter = 0;
    public long useCounter() {
        return ++counter;
    }

    @PostConstruct
    public void seedData() {
        JsonParserUser("src/main/resources/static/User_MockData.json");
        // --- Mock data for development ---
    }

    public void JsonParserUser(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        User[] users;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how user looks
            users = mapper.readValue(new File(src), User[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for (User user : users) {
            inMemoryDb.put(useCounter(), user);
        }
    }
}