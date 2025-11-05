package com.example.p3.service;

import com.example.p3.entities.Tool;
import com.example.p3.model.Favorites;
import com.example.p3.repositories.ToolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class FavoritesService {

    private final Map<Long, Favorites> inMemoryFavorites = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ToolRepository toolRepository;

    public List<Tool> getFavorites() {
        return toolRepository.findAll();
    }

    @PostConstruct
    public void seedDataFavorites() {
        JsonParserFavorites("classpath:static/MOCK_FAVORITES.json");
    }

    public void JsonParserFavorites(String src) {
        Favorites[] favs;

        try (InputStream is = resolveInputStream(src)) {
            favs = mapper.readValue(is, Favorites[].class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read favorites from '" + src + "'", e);
        }

        inMemoryFavorites.clear();
        for (Favorites f : favs) {
            inMemoryFavorites.put(f.getId(), f);
        }
    }

    private InputStream resolveInputStream(String src) throws IOException {
        if (src.startsWith("classpath:")) {
            String path = src.substring("classpath:".length());
            if (path.startsWith("/")) path = path.substring(1);
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is == null) throw new FileNotFoundException("Resource not found on classpath: " + path);
            return is;
        }
        return new FileInputStream(src);
    }
}
