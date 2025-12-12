package com.example.p3.repository;

import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FavoritesRepositoryTest {
    @Autowired
    private ToolRepository toolRepository;

    //It looks in tool repository, as that is where we call our favorite databse calls
    //This test looks into the actual database and checks how many favorites the employee has
    @Test
    void findFavoritesByEmployee() {
        List<Tool> tools = toolRepository.findFavoritesByEmployeeAndJurisdictionAndStage(
                "PEDO", "DK", "STAGING");
        assertNotNull(tools);
        assertEquals(3, tools.size());
    }
}