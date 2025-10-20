package com.example.p3;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class P3ApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("http://localhost:8080/user/getAll"))
                .andExpect(status().isOk());
                //.andExpect(content().string("Hello, World!"));
    }

}
