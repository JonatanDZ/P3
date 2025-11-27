package com.example.p3.controller;

import com.example.p3.entities.Department;
import com.example.p3.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    //Constructor for department
    public Department DepartmentConstructor(int id, String name, Boolean is_dev){
        Department department = new Department();
        department.setId(id);
        department.setName(name);
        department.setIs_dev(is_dev);

        return department;
    }
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    //Before each test, open mock
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        //Mock departments constructed
        Department department1 = DepartmentConstructor(1, "Devops", true);
        Department department2 = DepartmentConstructor(2, "HR", false);
        Department department3 = DepartmentConstructor(3, "Promotions", true);

        List<Department> departments = List.of(department1, department2, department3);

        //Checks if getAllDepartments returns all the correct values from the endpoint
        when(departmentService.getAllDepartments()).thenReturn(departments);
        mockMvc.perform(MockMvcRequestBuilders.get("/departments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //Since it is a list the index is specified for the values
                .andExpect(jsonPath("[0].id").value(1))
                .andExpect(jsonPath("[0].name").value("Devops"))
                .andExpect(jsonPath("[0]._dev").value(true))
                .andExpect(jsonPath("[1].id").value(2))
                .andExpect(jsonPath("[1].name").value("HR"))
                .andExpect(jsonPath("[1]._dev").value(false))
                .andExpect(jsonPath("[2].id").value(3))
                .andExpect(jsonPath("[2].name").value("Promotions"))
                .andExpect(jsonPath("[2]._dev").value(true));

    }
}
