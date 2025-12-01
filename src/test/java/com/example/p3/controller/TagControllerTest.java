package com.example.p3.controller;

import com.example.p3.entities.*;
import com.example.p3.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TagService tagService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public Tag tagConstructor(int id, String value, Set<Tool> tagTools) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setValue(value);
        tag.setTagTools(tagTools);
        return tag;
    }

    //Endpoint test: "/tags" GET
    @Test
    public void testGetTags() throws Exception {

        //Make mock tags
        Set<Tool> tagTools = new HashSet<>();
        Tag tag1 = tagConstructor(1, "tag1", tagTools);
        Tag tag2 = tagConstructor(2, "tag2", tagTools);
        Tag tag3 = tagConstructor(3, "tag3", tagTools);

        //Add tags to tag list
        List<Tag> tagList = new ArrayList<>(); // Make list and add the mock tools
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        // Ensures that we are fetching mock data created above
        when(tagService.getAllTags()).thenReturn(tagList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value").value("tag1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tools").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value").value("tag2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tools").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].value").value("tag3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].tools").isArray());
    }

    //Endpoint test: "/tags/id/1"
    //This tests that our endpoint can get one specific tool
    @Test
    public void testGetTag() throws Exception {

        //Make mock tag
        Set<Tool> tagTools = new HashSet<>();
        Tag tag = tagConstructor(1, "tag1", tagTools);

        // Ensures that we are fetching mock data created above
        when(tagService.getTagById(1)).thenReturn(Optional.ofNullable(tag));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tags/id/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value("tag1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tools").isArray());
    }

    //Endpoint test: "/tags" POST
    //This tests we can post a tool to the database with our endpoint
    @Test
    public void testAddTag() throws Exception {

        String validTag = "{\"value\":\"tag1\", \"tools\":\"[]\"}";

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validTag))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Endpoint test: "/tags/id/999" POST
    //This tests that our endpoint should return 404 Not Found if the tag id does not exist

    @Test
    public void testTag_NotFound() throws Exception {
        //Arrange it should return an empty list with a given tag id that does not exist
        when(tagService.getTagById(999)).thenReturn(Optional.empty());

        //Act calls the endpoint with a tag that does not exist
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/id/999"))
                //Assert that the http response is 404 Not Found
                .andExpect(status().isNotFound());
    }

    //Endpoint test "/tags"
    //This tests that our endpoint returns http 400 Bad Request if the tag does not have values
    @Test
    public void testAddTag_BadRequest() throws Exception {
        //Arrange an invalid tag with no values
        String invalidTag = "{\"value\", \"tools\":\"[]\"}";

        //Act a post request with our invalid tag
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTag))
                //Assert a Bad Request 400
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}