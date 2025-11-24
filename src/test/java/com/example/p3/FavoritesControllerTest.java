package com.example.p3;



import com.example.p3.controller.FavoritesController;
import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.entities.Tool;
import com.example.p3.entities.Tag;

import com.example.p3.service.FavoritesService;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

    public Employee EmployeeContructor(String initials, String name, String email, Department department, Set<Tool> favoriteTools) {
        Employee employee = new Employee();
        employee.setInitials(initials);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setFavoriteTools(favoriteTools);

        return employee;
    }

    public Tool toolConstructor(Integer id, String name, String url, Boolean is_personal, Boolean is_dynamic, Set<Department> departments, Set<Jurisdiction> jurisdictions, Set<Stage> stages, Set<Tag> tags) {
        Tool tool = new Tool();
        tool.setId(id);
        tool.setName(name);
        tool.setUrl(url);
        tool.setIs_personal(is_personal);
        tool.setIs_dynamic(is_dynamic);
        tool.setDepartments(departments);
        tool.setJurisdictions(jurisdictions);
        tool.setStages(stages);
        tool.setTags(tags);

        return tool;
    }

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
        Set<Stage> stagesSet = new HashSet<>();
        stagesSet.add(stage);
        Set<Tag> tagSet = new HashSet<>();
        Tool tool1 = toolConstructor(1,"tool1","https://tool1.com",true,false, departmentSet, jurisdictionSet, stagesSet, tagSet);
        Tool tool2 = toolConstructor(2,"tool2","https://tool2.com",false,false, departmentSet, jurisdictionSet, stagesSet, tagSet);

        //
        //Connect employee with favorite tools
        //
        employee.addFavorite(tool1);
        employee.addFavorite(tool2);

        when(favoritesService.getFavorites("JD","DK","PRODUCTION")).thenReturn(employee.getFavoriteTools().stream().toList());
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
}
