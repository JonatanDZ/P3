package com.example.p3;



import com.example.p3.controller.ToolController;
import com.example.p3.entities.*;
import com.example.p3.service.ToolService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;



//Tests the controller not the service
@WebMvcTest(ToolController.class)
public class ToolControllerTests {

    @Autowired
    private MockMvc mockMvc; // To test your web controllers without starting a full HTTP server

    @MockitoBean
    private ToolService toolService; // To mock toolService

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetToolById() throws Exception {
        Set<Department> departmentSet = new HashSet<>(); // Creates empty sets for related entities
        Set<Jurisdiction> jurisdictionSet = new HashSet<>();
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        // Create a mock tool
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

    @Test
    public void testGetTools() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        // Make mock tools
        Tool tool1 = new Tool(1,"testTool1","https://www.testing.dk",false,false,departmentSet,jurisdictionSet,stagesSet,tagSet);
        Tool tool2 = new Tool(2,"testTool2","https://www.testing2.dk",true,true,departmentSet,jurisdictionSet,stagesSet,tagSet);
        List<Tool> toolList = new ArrayList<>(); // Make list and add the mock tools
        toolList.add(tool1);
        toolList.add(tool2);
        when(toolService.getAllTools()).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/getTools"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("testTool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("https://www.testing.dk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isPersonal").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isDynamic").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stage").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("testTool2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].url").value("https://www.testing2.dk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].isPersonal").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].isDynamic").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stage").isArray());
    }

    @Test
    public void testGetToolsByDepartment() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Department dep = new Department(1,"DevOps",true); // Making mock department
        departmentSet.add(dep); // Add the mock department
        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        // Making a mock tool and adding it to the tool list
        Tool tool = new Tool(1,"testTool1","https://www.testing.dk",false,false,departmentSet,jurisdictionSet,stagesSet,tagSet);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        when(toolService.getAllToolsByDepartmentName("DEVOPS")).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/getTools/department/DEVOPS"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
}
