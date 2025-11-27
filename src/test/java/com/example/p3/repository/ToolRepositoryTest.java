package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.entities.Tool;
import com.example.p3.repositories.ToolRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToolRepositoryTest {
    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private EntityManager entityManager;

    // Testing: toolRepository.setStateOfPendingFalse(toolId);
    // Test creates a tool with pending = true, when using the tested method, it should become pending = false.
    @Test
    public void setStateOfPendingFalseTest(){
        // create tool with pending = false
        Tool testTool = new Tool(
                null,                   // id must be null for JPA to generate it
                "test",
                "test.com",
                false,                  // is_personal
                false,                  // is_dynamic
                new HashSet<>(),        // departments
                new HashSet<>(),        // jurisdictions
                new HashSet<>(),        // stages
                new HashSet<>(),        // tags
                true                    // pending
        );
        // saving to the live DB
        toolRepository.save(testTool);

        // use the method (act)
        toolRepository.setStateOfPendingFalse(testTool.getId());

        // since we use native SQL in the above query, JPATEST insists on looking at its own cache which only holds the info
        // from the original tool.
        // the repository call uses a modifying statement (!), so we have to clear JPATEST's cache in order to fetch the updated tool from the db
        entityManager.flush();
        entityManager.clear();

        // fetch from the DB and assert that it has changed the pending from true to false
        Tool updatedTool = toolRepository.findById(testTool.getId())
                .orElseThrow();
        assertFalse(updatedTool.getPending());
    }


}
