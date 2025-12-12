package com.example.p3.controller;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.entities.Tag;

import com.example.p3.service.FavoritesService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(FavoritesController.class)
class FavoritesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FavoritesService favoritesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Employee constructor
    public Employee EmployeeContructor(String initials, String name, String email, Department department, Set<Tool> favoriteTools) {
        Employee employee = new Employee();
        employee.setInitials(initials);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setFavoriteTools(favoriteTools);

        return employee;
    }

    //Tool constructor
    public Tool toolConstructor(Integer id, String name, String url, Boolean is_personal, Boolean is_dynamic, Set<Department> departments, Set<Jurisdiction> jurisdictions, Set<Stage> stage, Set<Tag> tags) {
        Tool tool = new Tool();
        tool.setId(id);
        tool.setName(name);
        tool.setUrl(url);
        tool.setIs_personal(is_personal);
        tool.setIs_dynamic(is_dynamic);
        tool.setDepartments(departments);
        tool.setJurisdictions(jurisdictions);
        tool.setStages(stage);
        tool.setTags(tags);

        return tool;
    }

    //Testing: /employee/JD/favorites?jurisdiction=DK&stage=PRODUCTION GET
    @Test
    void favoritesListsForAllUsers() throws Exception {
        //
        //All to mock employee
        //
        //Mock department
        Department hr = new Department();
        hr.setId(1);
        hr.setName("HR");
        Set<Tool> emptyToolSet = new HashSet<>();

        Stage stage = new Stage();
        stage.setId(1);
        stage.setName("PRODUCTION");

        Jurisdiction jurisdiction = new Jurisdiction();
        jurisdiction.setId(1);
        jurisdiction.setName("DK");

        //Make mock employees
        Employee employee = EmployeeContructor("JD", "John Doe", "SomeEmail", hr, emptyToolSet);

        //
        // All to mock tools
        //
        //Make mock tools
        Set<Department> departmentSet = new HashSet<>();
        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for
        jurisdictionSet.add(jurisdiction);
        Set<Stage> stageSet = new HashSet<>();
        stageSet.add(stage);
        Set<Tag> tagSet = new HashSet<>();
        Tool tool1 = toolConstructor(1,"tool1","https://tool1.com",true,false, departmentSet, jurisdictionSet, stageSet, tagSet);
        Tool tool2 = toolConstructor(2,"tool2","https://tool2.com",false,false, departmentSet, jurisdictionSet, stageSet, tagSet);
        List<Tool> toolList = new ArrayList<>();

        //
        //Connect employee with favorite tools
        // Using an in-memory ArrayList instead of the entity - when using the entity it produces an unexpected result
        toolList.add(tool1);
        toolList.add(tool2);

        when(favoritesService.getFavorites("JD","DK","PRODUCTION")).thenReturn(toolList);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/employee/JD/favorites?jurisdiction=DK&stage=PRODUCTION"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].name").value("tool1"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].url").value("https://tool1.com"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].is_personal").value(true))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].is_dynamic").value(false)) //Maybe removed if we swap the DTO
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].name").value("tool2"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].url").value("https://tool2.com"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].is_personal").value(false))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].is_dynamic").value(false));
    }

    // Testing: /employee/PEDO/favorites/1
    // Mocks the service method which should return the tool given that its bridging tables were updated to
    // become a favorite tool
    // then asserting that the post method returns 200 ok and returns a JSON object with the tool made to a favorite.
    @Test
    public void testToggleFavorites() throws Exception{
        Department hr = new Department();
        hr.setId(1);
        hr.setName("HR");

        Set<Department> departmentSet = new HashSet<>();
        departmentSet.add(hr);
        Set<Jurisdiction> jurisdictionSet = new HashSet<>();
        Set<Stage> stageSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();

        Tool tool = toolConstructor(
                1,
                "tool1",
                "https://tool1.com",
                true,
                false,
                departmentSet,
                jurisdictionSet,
                stageSet,
                tagSet
        );

        when(favoritesService.toggleFavorite("PEDO", 1)).thenReturn(tool);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee/PEDO/favorites/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tools\":\"[]\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://tool1.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_personal").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_dynamic").value(false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Testing: /employee/PEDO/favorites/1, method: DELETE
    // basically does the same as above, the request method is just different and service method.
    @Test
    public void testUntoggleFavorites() throws Exception{
        Department hr = new Department();
        hr.setId(1);
        hr.setName("HR");

        Set<Department> departmentSet = new HashSet<>();
        departmentSet.add(hr);
        Set<Jurisdiction> jurisdictionSet = new HashSet<>();
        Set<Stage> stageSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();

        Tool tool = toolConstructor(
                1,
                "tool1",
                "https://tool1.com",
                true,
                false,
                departmentSet,
                jurisdictionSet,
                stageSet,
                tagSet
        );

        when(favoritesService.untoggleFavorite("PEDO", 1)).thenReturn(tool);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employee/PEDO/favorites/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tools\":\"[]\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tool1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://tool1.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_personal").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_dynamic").value(false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}