package com.example.p3;

import com.example.p3.controller.TagController;
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

    @Test
    public void testAddTag() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\":\"tag1\", \"tools\":\"[]\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}