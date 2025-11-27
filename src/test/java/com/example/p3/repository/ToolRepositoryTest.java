package com.example.p3.repository;

import com.example.p3.entities.*;
import com.example.p3.repositories.EmployeeRepository;
import com.example.p3.repositories.ToolRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.apache.bcel.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToolRepositoryTest extends RepositoryGlobalMethods {
    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepository employeeRepository;

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

    // Testing: toolRepository.setStateOfPendingFalse(toolId);
    // Edge case: given that a tool is already pending = false, it should remain so.
    @Test
    public void setStateOfPendingFalseEdgeCaseTest() {
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
                false                    // pending - IMPORTANT. This tests the edge case
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

    // Testing: toolRepository.findPendingToolByUserDepartment(departmentName);
    // creates department and tool with pending = true. Expects that the list of tools returned from the above method includes the tool created
    // BECAUSE it has pending = true.
    @Test
    public void findPendingToolByUserDepartmentTest() {
        // creating a set of departments only containing the testDepartment.
        Department testDepartment = createDepartment();
        Set<Department> departments = new HashSet<>();
        departments.add(testDepartment);

        // create a tool, where department is the testDepartment
        Tool testTool = new Tool(
                null,                   // id must be null for JPA to generate it
                "test",
                "test.com",
                false,                  // is_personal
                false,                  // is_dynamic
                departments,        // departments - IMPORTANT
                new HashSet<>(),        // jurisdictions
                new HashSet<>(),        // stages
                new HashSet<>(),        // tags
                true                    // pending - IMPORTANT
        );

        // saving to the live DB
        toolRepository.save(testTool);

        // only looking at the single testDepartment made in line 71
        List<Tool> pendingTools = toolRepository.findPendingToolByUserDepartment(testDepartment.getName());

        // assert that the list has the pending tool in it
        assertTrue(pendingTools.contains(testTool));
    }

    // Testing: toolRepository.findFavoritesByEmployeeAndJurisdictionAndStage(employeeInitials, jurisdictionName, stageName)
    // Retrieve a list of favorites associated with the user, current jur and stage. Expect that the tool uploaded is in the list
    // of favorites returned. Under the assumption of being in the testJur and testStage.
    @Test
    public void findFavoritesByEmployeeJurisdictionAndStageTest() {
        // add a favorite tool to specific employee, jurisdiction and stage
        // creating a set of departments only containing the testDepartment.
        Department testDepartment = createDepartment();
        Set<Department> departments = new HashSet<>();
        departments.add(testDepartment);

        // creating an employee. Saving it to the database, else the foreign key constraints will trigger and throw error
        Employee employee = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", testDepartment);
        employeeRepository.save(employee);

        // creating a set of jurisdictions
        Jurisdiction testJurisdiction = createJurisdiction();
        Set<Jurisdiction> jurisdictions = new HashSet<>();
        jurisdictions.add(testJurisdiction);

        // creating a set of stages
        Stage testStage = createStage();
        Set<Stage> stages = new HashSet<>();
        stages.add(testStage);

        // create a tool, where employee is testEmployee, jurisdiction is testJurisdiction and stage is testStage
        Tool testTool = new Tool(
                null,                   // id must be null for JPA to generate it
                "test",
                "test.com",
                false,                  // is_personal
                false,                  // is_dynamic
                departments,        // departments - IMPORTANT
                jurisdictions,        // jurisdictions
                stages,        // stages
                new HashSet<>(),        // tags
                true                    // pending - IMPORTANT
        );

        // saving tool to mock db
        toolRepository.save(testTool);

        // updating bridging tables
        // assumes that toggleAsFavorite works as intended. This might cause a false negative in the test runs.
        toolRepository.toggleAsFavorite(employee.getInitials(), testTool.getId());

        // act
        List<Tool> favoritesReturned = toolRepository.findFavoritesByEmployeeAndJurisdictionAndStage(employee.getInitials(), testJurisdiction.getName(), testStage.getName());

        // test that the favorite tool is in the list returned by the repo call
        assertTrue(favoritesReturned.contains(testTool));
    }

    @Test
    public void toggleAsFavoriteTest() {
        // Testing: toolRepository.toggleAsFavorite(employeeInitials, toolId);
        // Given an employee and a tool in the same department/jurisdiction/stage,
        // when toggleAsFavorite is called, the tool should appear in the list of favorites
        // and calling it again should not create duplicate favorites.

        // arrange: create department set
        Department testDepartment = createDepartment();
        Set<Department> departments = new HashSet<>();
        departments.add(testDepartment);

        // arrange: create and persist employee
        Employee employee = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", testDepartment);
        employeeRepository.save(employee);

        // arrange: create jurisdiction set
        Jurisdiction testJurisdiction = createJurisdiction();
        Set<Jurisdiction> jurisdictions = new HashSet<>();
        jurisdictions.add(testJurisdiction);

        // arrange: create stage set
        Stage testStage = createStage();
        Set<Stage> stages = new HashSet<>();
        stages.add(testStage);

        // arrange: create and persist tool linked to department/jurisdiction/stage
        Tool testTool = new Tool(
                null,
                "test",
                "test.com",
                false,              // is_personal
                false,              // is_dynamic
                departments,        // departments
                jurisdictions,      // jurisdictions
                stages,             // stages
                new HashSet<>(),    // tags
                true                // pending
        );
        toolRepository.save(testTool);

        // before toggling, there should be no favorites for this combination
        List<Tool> favoritesBefore = toolRepository
                .findFavoritesByEmployeeAndJurisdictionAndStage(
                        employee.getInitials(),
                        testJurisdiction.getName(),
                        testStage.getName()
                );
        assertTrue(favoritesBefore.isEmpty());

        // act: first toggle should insert a favorite
        toolRepository.toggleAsFavorite(employee.getInitials(), testTool.getId());

        List<Tool> favoritesAfterFirstToggle = toolRepository
                .findFavoritesByEmployeeAndJurisdictionAndStage(
                        employee.getInitials(),
                        testJurisdiction.getName(),
                        testStage.getName()
                );

        // assert that the tool is now in the favorites list
        assertTrue(favoritesAfterFirstToggle.contains(testTool));


        // act: second toggle should not create duplicates due to WHERE NOT EXISTS
        toolRepository.toggleAsFavorite(employee.getInitials(), testTool.getId());

        List<Tool> favoritesAfterSecondToggle = toolRepository
                .findFavoritesByEmployeeAndJurisdictionAndStage(
                        employee.getInitials(),
                        testJurisdiction.getName(),
                        testStage.getName()
                );

        long countOfTestTool = favoritesAfterSecondToggle.stream()
                .filter(t -> t.getId().equals(testTool.getId()))
                .count();

        // assert that the tool is still only present once
        assertEquals(1, countOfTestTool);
    }

}
