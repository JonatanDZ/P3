package com.example.p3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class P3ApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();


    @Test
    void testGetEmployeeByInitials() throws Exception {
        // what would happen to this test if "PEDO" did not exist?
        // is it safe to assume that "PEDO" will always exist in our database?

        // Defines the REST endpoint to test
        String url = "http://localhost:8080/employee/initials/PEDO";

        // restTemplate has REST requests as methods, we use the GET request here.
        // we pass the url into the method; this creates a GET request to the endpoint
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        // maps from JSON into tree-like objects (jsonNode or POJOs)
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readValue(response.getBody(), JsonNode.class);
        //  Using the result from response mapped to a JsonNode.
        // searches through the nodes and finds the one called "initials" and transforms it to a string
        String initials = root.path("initials").toString();

        String expectedJson = "\"PEDO\"";

        // asserting that the string is the one expected.
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(expectedJson,initials);
    }

    //Tests for initials, email and name work the same
    @Test
    void testGetEmployeeByDepartment() throws Exception {
        // what would happen to this test if id=1 did not exist?
        // is it safe to assume that id=1 will always exist and be the
        // same as the expected value in our database?
        String url = "http://localhost:8080/employee/department/1";

        // GET request stored as response
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode[] root = mapper.readValue(response.getBody(), JsonNode[].class);

        // asserting that we get a response of 200 ok
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedJson = "\"DEVOPS\"";
        for (JsonNode node : root) {
            // going through every node in root and checking if the expectedJson matches the department names
            Assertions.assertEquals(expectedJson, node.path("department").toString());
        }
    }

    //Test to see if getFavoriteTools is called


    @Test
    void favoritesDetailedListsForAllUsers() throws Exception {
        String url = "http://localhost:8080/getTools/getFavoriteTools/details/2";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String expectedJson = """
                {"id":2,"toolIDs":[
                {"id":4,"name":"Kubernetes Production Best Practices",
                "url":"https://kubernetes.io/docs/concepts/security/",
                "tags":["kubernetes","devops","security"],
                "departments":["DEVOPS"],
                "stages":["PRODUCTION"],
                "jurisdictions":["DK","UK"],
                "dynamic":false}]}
                """;

        // why not Assertions.assertEquals?
        // can this be made to look like the previous tests?
        assertThat(objectMapper.readTree(response.getBody())).isEqualTo((objectMapper.readTree(expectedJson)));
    }
//    @Autowired
//    private MockMvc mockMvc;
////    @Test
////    public void testGetAllEndpoint() throws Exception {
////        mockMvc.perform(get("/getUsers"))
////                .andExpect(status().isOk());
////                //.andExpect(content().string("Hello, World!"));
////    }
//
//    @Test
//    public void testIdEndpoint() throws Exception {
//        mockMvc.perform(get("/getUsers/id/2"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists());
//    }

}
