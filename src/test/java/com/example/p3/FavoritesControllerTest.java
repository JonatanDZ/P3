package com.example.p3;

import com.example.p3.controller.FavoritesController;
import com.example.p3.entities.*;
import com.example.p3.service.FavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(FavoritesController.class)
public class FavoritesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FavoritesService favoritesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getEmployeeFavorites() throws Exception{
        Set<Department> departments = new HashSet<>();
        Set<Jurisdiction> jurisdictions = new HashSet<>();
        Set<Stage> stages = new HashSet<>();
        Set<Tag> tags = new HashSet<>();

        Tool slack = new Tool(
                2,
                "Slack",
                "https://slack.com",
                false,
                false,
                departments,
                jurisdictions,
                stages,
                tags
        );

        Tool spilNu = new Tool(
                9,
                "Spil Nu Production",
                "https://spilnu.dk",
                true,
                true,
                departments,
                jurisdictions,
                stages,
                tags
        );
        List<Tool> favList = List.of(slack, spilNu);

        when(favoritesService.getFavorites("PEDO", "DK", "STAGING"))
                .thenReturn(favList);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/PEDO/favorites")
                        .param("jurisdiction", "DK")
                        .param("stage", "STAGING"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Slack"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("https://slack.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].is_dynamic").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stages").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(9))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Spil Nu Production"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].url").value("https://spilnu.dk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].is_dynamic").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stages").isArray());
    }
}