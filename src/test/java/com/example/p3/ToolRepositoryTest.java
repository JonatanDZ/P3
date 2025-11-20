package com.example.p3;

import com.example.p3.entities.Employee;
import com.example.p3.entities.Tool;
import com.example.p3.repositories.EmployeeRepository;
import com.example.p3.repositories.ToolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToolRepositoryTest {

    @Autowired
    private ToolRepository toolRepository;

    @Test
    public void testgetAllTools(){
        List<Tool> tool = toolRepository.findAll();
        assertNotNull(tool);
        for(int i = 0; i < tool.size(); i++){
            assertEquals(i+1, tool.get(i).getId());
        }
    }
}

