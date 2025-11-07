package com.example.p3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FavoritesControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();

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
}
