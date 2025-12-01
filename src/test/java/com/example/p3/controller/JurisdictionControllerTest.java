package com.example.p3.controller;

import com.example.p3.entities.Jurisdiction;
import com.example.p3.service.JurisdictionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(JurisdictionController.class)
public class JurisdictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JurisdictionService JurisdictionService;
    @Autowired
    private JurisdictionService jurisdictionService;

    @Test
    void testGetAllJurisdictions() throws Exception{
        Jurisdiction jurisdiction1 = new Jurisdiction();
        jurisdiction1.setId(1);
        Jurisdiction jurisdiction2 = new Jurisdiction();
        jurisdiction2.setId(2);
        Jurisdiction jurisdiction3 = new Jurisdiction();
        jurisdiction3.setId(3);
        List<Jurisdiction> jurisdictions = new ArrayList<>();
        jurisdictions.add(jurisdiction1);
        jurisdictions.add(jurisdiction2);
        jurisdictions.add(jurisdiction3);

        //Arrange service to return the amount of populated jurisdictions
        when(jurisdictionService.getAllJurisdictions())
                .thenReturn(jurisdictions);

        //Act
        mockMvc.perform(MockMvcRequestBuilders.get("/jurisdictions"))
                //Assert that the response http is 200 and the returned list size is the actual populated size
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(jurisdictions.size()));
    }

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
