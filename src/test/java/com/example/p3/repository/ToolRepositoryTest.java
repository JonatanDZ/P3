package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToolRepositoryTest {
    @Autowired
    private ToolRepository toolRepository;

    @Test
    public void setStateOfPendingFalse(){
        // create tool with pending = false
        // insert into mock db


        // use the method (act)
        // assert that the state of pending = false
    }

}
