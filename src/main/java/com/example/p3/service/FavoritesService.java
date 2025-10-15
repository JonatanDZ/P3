package com.example.p3.service;

import com.example.p3.model.Favorites;
import com.example.p3.model.Tool;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FavoritesService {

    private final Map<Long, Favorites> inMemoryFavorites = new ConcurrentHashMap<>();

    static long counter = 0;
    public long useCounter() {
        return ++counter;
    }

    public Map<Long, Favorites> getFavorites() {
        return inMemoryFavorites;
    }

    @PostConstruct
    public void seedData() {
        JsonParser("src/main/resources/static/MOCK_FAVORITES.json");
    }
    public void JsonParser(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Favorites[] tools;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how tool looks
            tools = mapper.readValue(new File(src), Favorites[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for(Favorites tool : tools){
            inMemoryFavorites.put(useCounter(), tool);
        }
    }
}
