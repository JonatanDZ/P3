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

    public Map<Long, Favorites> getFavorites() {
        return inMemoryFavorites;
    }

    //@PostConstruct
    public void seedData() {
        JsonParserFavorites("src/main/resources/static/MOCK_FAVORITES.json");
    }

    public void JsonParserFavorites(String src) {
        ObjectMapper mapper = new ObjectMapper();

        Favorites[] favs;

        // I needed to make a try/catch otherwise it complained.
        try {
            favs = mapper.readValue(new File(src), Favorites[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inMemoryFavorites.clear();
        //We are posting all the elements to the DB.
        for(Favorites f : favs){
            inMemoryFavorites.put(f.getId(), f);
        }
    }
}
