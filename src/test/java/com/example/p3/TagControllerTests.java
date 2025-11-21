package com.example.p3;

import com.example.p3.controller.TagController;
import com.example.p3.entities.Tag;
import com.example.p3.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

@WebMvcTest(TagController.class)
public class TagControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TagService tagService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTags() throws Exception {
        Set<Tag> tagTools = new HashSet<>();
    }
}
