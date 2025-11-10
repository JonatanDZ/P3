package com.example.p3;



import com.example.p3.controller.ToolController;
import com.example.p3.entities.*;
import com.example.p3.service.ToolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.Mockito.*;


@WebMvcTest(ToolController.class)
public class ToolsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ToolService toolService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetToolById() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Set<Jurisdiction> jurisdictionSet = new HashSet<>();
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        Tool tool = new Tool(1,"testTool","https://www.geeksforgeeks.org/software-testing/crud-junit-tests-for-spring-data-jpa/",false,false,departmentSet,jurisdictionSet,stagesSet,tagSet);
        when(toolService.getToolById(1)).thenReturn(Optional.of(tool));
        mockMvc.perform(MockMvcRequestBuilders.get("/getTools/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testTool"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://www.geeksforgeeks.org/software-testing/crud-junit-tests-for-spring-data-jpa/"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isPersonal").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isDynamic").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stage").isArray());
    }
}
