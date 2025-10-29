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
    void testGetUserById() throws Exception {
        String url = "http://localhost:8080/getUsers/id/15";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readValue(response.getBody(), JsonNode.class);
        String id = root.path("id").toString();

        String expectedJson = "15";

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(expectedJson,id);
    }

    //Tests for initials, email and name work the same

    @Test
    void testGetUserByDepartment() throws Exception {
        String url = "http://localhost:8080/getUsers/department/DEVOPS";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode[] root = mapper.readValue(response.getBody(), JsonNode[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedJson = "\"DEVOPS\"";
        for (JsonNode node : root) {
            Assertions.assertEquals(expectedJson, node.path("department").toString());

        }
    }

    //Test to see if getFavoriteTools is called
    @Test
    void favoritesListsForAllUsers() throws Exception {
        String url = "http://localhost:8080/getTools/getFavoriteTools";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String expectedJson = """
                [
                  { "id": 1, "toolIDs": [1, 3] },
                  { "id": 2, "toolIDs": [4] }
                ]
                """;

        assertThat(objectMapper.readTree(response.getBody())).isEqualTo((objectMapper.readTree(expectedJson)));
    }

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
