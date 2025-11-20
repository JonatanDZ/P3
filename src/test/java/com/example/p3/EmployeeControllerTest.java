package com.example.p3;

import com.example.p3.entities.Tool;
import com.example.p3.controller.EmployeeController;
import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    public Employee EmployeeContructor(String initials, String name, String email, Department department, Set<Tool> favoriteTools) {
        Employee employee = new Employee();
        employee.setInitials(initials);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setFavoriteTools(favoriteTools);

        return employee;
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEmployeesByInitials() throws Exception {
        Department hr = new Department();
        hr.setId(1);
        hr.setName("HR");

        Department devOps = new Department();
        devOps.setId(2);
        devOps.setName("devOps");

        Set<Tool> emptyToolSet = new HashSet<>();
        //Make mock employees
        Employee employee1 = EmployeeContructor("JD", "John Doe", "SomeEmail", hr, emptyToolSet);
        Employee employee2 = EmployeeContructor("ÅS", "Ålice Smith", "AnotherEmail", devOps, emptyToolSet);


        when(employeeService.getEmployeeByInitials("JD")).thenReturn(Optional.ofNullable(employee1));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/JD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.initials").value("JD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("SomeEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department_name").value("HR"));

        when(employeeService.getEmployeeByInitials("ÅS")).thenReturn(Optional.ofNullable(employee2));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/ÅS"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.initials").value("ÅS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ålice Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("AnotherEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department_name").value("devOps"));
    }
}
