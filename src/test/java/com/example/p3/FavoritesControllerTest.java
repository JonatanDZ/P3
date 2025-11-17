package com.example.p3;

import com.example.p3.controller.FavoritesController;
import com.example.p3.entities.Department;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.service.EmployeeService;
import com.example.p3.service.FavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

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

    public void getEmployeeFavorites() throws Exception{
        Set<Department> departmentSet = new HashSet<>();
        Set<Jurisdiction> jurisdictionSet = new HashSet<>(); //Creates empty sets for related entities
        Set<Stage> stagesSet = new HashSet<>();
        
    }
}