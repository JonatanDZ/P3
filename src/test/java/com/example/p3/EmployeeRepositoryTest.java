package com.example.p3;

import com.example.p3.entities.Employee;
import com.example.p3.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    //This test looks into the actual database and tries to find the actual employee
    @Test
    public void testFindByInitials(){
        Optional<Employee> employees = employeeRepository.findByInitials("PEDO");
        assertNotNull(employees);
        assertTrue(employees.isPresent());
    }

    //This test will look for an employee that is not present in the database and fail
    @Test
    public void testFindByInitialsNotPresent(){
        Optional<Employee> employees = employeeRepository.findByInitials("KOLO");
        assertTrue(employees.isEmpty(), "Employee not found");
    }

    //This test looks into the actual database and finds all employees
    @Test
    public void testGetAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);
        assertEquals(14, employees.size());
    }
}
