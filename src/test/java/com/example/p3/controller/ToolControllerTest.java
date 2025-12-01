package com.example.p3.controller;

import com.example.p3.entities.*;
import com.example.p3.repository.RepositoryGlobalMethods;
import com.example.p3.service.ToolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


//Tests the controller not the service.
@WebMvcTest(ToolController.class)
public class ToolControllerTest extends RepositoryGlobalMethods {

    @Autowired
    private MockMvc mockMvc; // To test your web controllers without starting a full HTTP server

    @MockBean
    private ToolService toolService; // To mock toolService

    @Test
    public void testGetAllTools() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();

        // Make mock tools
        Tool tool1 = new Tool(1, "testTool1", "https://www.testing.dk", false,false, departmentSet, jurisdictionSet, stagesSet, tagSet, false, createEmployee());
        Tool tool2 = new Tool(2, "testTool2", "https://www.testing2.dk", true, false, departmentSet, jurisdictionSet, stagesSet, tagSet, false, createEmployee());
        List<Tool> toolList = new ArrayList<>(); // Make list and add the mock tools
        toolList.add(tool1);
        toolList.add(tool2);

        when(toolService.getAllToolsExcludingPending()).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tools"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                //Tool1
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("testTool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("https://www.testing.dk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].is_personal").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].is_dynamic").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departments").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stage").isArray())
                //Tool2 doesn't have department attached and cannot be dynamic
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("testTool2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].url").value("https://www.testing2.dk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].is_personal").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].is_dynamic").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departments").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].jurisdictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].stage").isArray());

    }

    @Test
    public void testGetToolsByDepartment() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Department dep = new Department(1, "DevOps", true); // Making mock department
        departmentSet.add(dep); // Add the mock department
        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        // Making a mock tool and adding it to the tool list
        Tool tool = new Tool(1, "testTool1", "https://www.testing.dk", false, false, departmentSet, jurisdictionSet, stagesSet, tagSet, false, createEmployee());
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);

        when(toolService.getAllToolsByDepartmentName("DEVOPS")).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tools/department/DEVOPS"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }

    @Test
    public void testAddTools() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"tag1\", \"tools\":\"[]\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testing: /tools/department/{department}/jurisdiction/{jurisdiction}/stage/{stage}
    // Giving it a list of two tools belonging to the same jur, stage and department.
    // It should give a 200 ok status code, and return the list in JSON form.
    @Test
    public void testToolsByDepartmentJurisdictionStage() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Department dep = new Department(1, "DevOps", true); // Making mock department
        departmentSet.add(dep); // Add the mock department

        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Jurisdiction jur = new Jurisdiction();
        jur.setName("DK");
        jurisdictionSet.add((jur));


        Set<Stage> stagesSet = new HashSet<>();
        Stage stageTest = new Stage();
        stageTest.setName("Development");

        Set<Tag> tagSet = new HashSet<>();

        // Making a mock tool and adding it to the tool list
        Tool tool1 = new Tool(1, "testTool1", "https://www.testing.dk", false, false, departmentSet, jurisdictionSet, stagesSet, tagSet, false, createEmployee());
        Tool tool2 = new Tool(2, "testTool2", "https://www.testing2.dk", false, false, departmentSet, jurisdictionSet, stagesSet, tagSet, false, createEmployee());

        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool1);
        toolList.add(tool2);

        when(toolService.getAllToolsByDepartmentJurisdictionStage("DevOps", "DK", "Development")).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tools/department/DevOps/jurisdiction/DK/stage/Development"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("testTool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("testTool2"));
    }

    // Testing: /tools/pending/department/{department}
    // Giving it a list of two tools belonging to the same department where pending = true
    // It should give a 200 ok status code, and return the list in JSON form.
    @Test
    public void testPendingToolsByDepartment() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Department dep = new Department(1, "DevOps", true); // Making mock department
        departmentSet.add(dep); // Add the mock department

        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();

        // Making a mock tool and adding it to the tool list
        Tool tool1 = new Tool(1, "testTool1", "https://www.testing.dk", false, false, departmentSet, jurisdictionSet, stagesSet, tagSet, true, createEmployee());
        Tool tool2 = new Tool(2, "testTool2", "https://www.testing2.dk", false, false, departmentSet, jurisdictionSet, stagesSet, tagSet, true, createEmployee());

        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool1);
        toolList.add(tool2);


        when(toolService.findPendingToolByUserDepartment("DevOps")).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tools/pending/department/DevOps"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("testTool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pending").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("testTool2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].pending").value(true));
    }

    // Testing: /tools/pending/{toolId}, DELETE method
    // The service method used in this endpoint returns void, so it cannot be mocked.
    // Given that it deletes nothing, it should return no content. The test is useful, because it can still return 404, indicating something is wrong with the endpoint.
    @Test
    public void testDeletePendingTool() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tools/pending/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // Testing: /tools/pending/department/{department}
    // Giving it a single tool with pending = false, to mock that the service method reverts its state.
    // check that it returns 200 ok, the tool provided and that pending = false.
    @Test
    public void testUpdatePendingTool() throws Exception {
        Set<Department> departmentSet = new HashSet<>();
        Department dep = new Department(1, "DevOps", true); // Making mock department
        departmentSet.add(dep); // Add the mock department

        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();

        // Making a mock tool and adding it to the tool list
        Tool tool1 = new Tool(1, "testTool1", "https://www.testing.dk", false, false, departmentSet, jurisdictionSet, stagesSet, tagSet, false, createEmployee());

        // mocks the service method used in the endpoint.
        // it actually takes a tool with pending = true, and makes it pending = false. We mock that, by saying pending = false from the start.
        when(toolService.revertStateOfPending(1)).thenReturn(tool1);
        mockMvc.perform(MockMvcRequestBuilders.put("/tools/pending/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testTool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pending").value(false));
    }


/*@Test
=======
    /*@Test
>>>>>>> Stashed changes
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
    }*/

}