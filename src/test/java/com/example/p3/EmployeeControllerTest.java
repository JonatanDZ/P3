package com.example.p3;

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

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getEmployeesFromInitials() throws Exception {
        Department departmentSet = new Department();
        Employee employee =  new Employee("SOJO", "Sofus Johansen", "SoJo@gmail.com", departmentSet);
        when(employeeService.getEmployeeByInitials("SOJO"))
                .thenReturn(Optional.of(employee));
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/initials/SOJO"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.initials").value("SOJO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sofus Johansen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("SoJo@gmail.com"));
    }
}
