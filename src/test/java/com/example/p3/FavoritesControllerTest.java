package com.example.p3;

import com.example.p3.controller.FavoritesController;
import com.example.p3.service.EmployeeService;
import com.example.p3.service.FavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FavoritesController.class)
class FavoritesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FavoritesService favoritesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public void getEmployeeFavorites() throws Exception{

    }
}