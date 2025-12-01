package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.repositories.DepartmentRepository;
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
public class DepartmentRepositoryTest extends RepositoryGlobalMethods {
    @Autowired
    private DepartmentRepository departmentRepositoryTest;

    @Test
    public void testDepartmentById(){
        Department d1 = new Department(null, "Frontend", true); //id must be null for JPA to generate it
        //to ensure the correct id is used it will be saved and store in a variable "saved" which is then referenced
        Department saved = departmentRepositoryTest.save(d1);

        Optional<Department> department = departmentRepositoryTest.findById(saved.getId());
        assertNotNull(department);
        assertTrue(department.isPresent());
    }

    @Test
    public void testDepartmentByIdNotPresent(){
        Optional<Department> department = departmentRepositoryTest.findById(10);
        //Assert true that the employee returned is empty, as there is no employee with initials KOLO
        assertTrue(department.isEmpty(), "Department not found");
    }

    @Test
    public void testGetAllDepartment(){ //Check the amount of saved departments are in the repository
        //Mock departments are constructed
        Department d1 = new Department(null, "Frontend", true); //id must be null for JPA to generate it
        Department d2 = new Department(null, "Legal", false);
        Department d3 = new Department(null, "HR", false);
        Department d4 = new Department(null, "Devops", true);
        Department d5 = new Department(null, "Payments", true);

        //Mock departments are saved in the repository
        departmentRepositoryTest.save(d1);
        departmentRepositoryTest.save(d2);
        departmentRepositoryTest.save(d3);
        departmentRepositoryTest.save(d4);
        departmentRepositoryTest.save(d5);

        //The departments are put into a list
        List<Department> department = departmentRepositoryTest.findAll();
        assertNotNull(department);
        assertEquals(5, department.size());
    }
}
