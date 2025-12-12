package com.example.p3.controller;
import com.example.p3.entities.Tool;
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
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    //Constructor for employee
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

    //Before each test, open mock
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Ensures the endpoint correctly reaches the controller method
    //and the JSON output from the controller is correct.
    //Also it checks that our HTTP status is correct (200)
    // Testing: /employee/initials/JD GET
    @Test
    public void testGetEmployeesByInitials() throws Exception {
        //Create 2 mock departments
        Department hr = new Department();
        hr.setId(1);
        hr.setName("HR");
        hr.setIs_dev(false);

        Department devOps = new Department();
        devOps.setId(2);
        devOps.setName("devOps");
        devOps.setIs_dev(true);

        //Mock favorites list
        Set<Tool> emptyToolSet = new HashSet<>();

        //Make mock employees
        Employee employee1 = EmployeeContructor("JD", "John Doe", "SomeEmail", hr, emptyToolSet);
        Employee employee2 = EmployeeContructor("ÅS", "Ålice Smith", "AnotherEmail", devOps, emptyToolSet);

        //When getting JD return mock employee JD and check that it matches
        when(employeeService.getEmployeeByInitials("JD")).thenReturn(Optional.ofNullable(employee1));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/JD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.initials").value("JD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("SomeEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department_name").value("HR"));

        //When getting Ås return mock employee ÅS and check that it matches
        when(employeeService.getEmployeeByInitials("ÅS")).thenReturn(Optional.ofNullable(employee2));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/ÅS"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.initials").value("ÅS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ålice Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("AnotherEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department_name").value("devOps"));
    }

    //This test is to check it returns not found (404) when the initial doesn't exit.
    // Testing: /employee/initials/ GET
    @Test
    public void testGetEmployeeByInitials_NotFound() throws Exception {
        //Arrange; that the service should returns Optional.empty given initials that are not found
        String initials = "WRNG";
        when(employeeService.getEmployeeByInitials(initials))
                .thenReturn(Optional.empty());
        //Act; perform the get request to the controller with the wrong initals
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/" + initials))
                //Assert; a http status is 404 Not Found
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //This test is to check it returns error (400) when the input isn't 4 initials.
    // Testing: /employee/initials/ GET
    @Test
    public void testGetEmployeeByInitials_BadInput() throws Exception {
        //Arrange a bad input, and we will not call the service, since we expect the controller to reject the request
        String badInput = "@A!1";

        //Act - perform the get request to the controller with the bad request
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/" + badInput))
                //Assert - the http status is 400 Bad Request
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}