package com.example.p3.controller;

import com.example.p3.entities.Tool;
import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.service.EmployeeService;
import com.example.p3.service.JurisdictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(JurisdictionController.class)
public class JurisdictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JurisdictionService JurisdictionService;
    @Autowired
    private JurisdictionService jurisdictionService;

    //Test that the controller returns 200 and an empty list if there are no jurisdictions
    @Test
    void testGetAllJurisdictions_EmptyList() throws Exception {
        //Arrange service to return an empty list
        when(jurisdictionService.getAllJurisdictions())
                .thenReturn(Collections.emptyList());

        //Act and assert that the http is 200 and the returned list is an empt json body
        mockMvc.perform(MockMvcRequestBuilders.get("/jurisdictions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

    }
}
