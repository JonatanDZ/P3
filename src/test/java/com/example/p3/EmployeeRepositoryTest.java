package com.example.p3;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByInitials(){
        Optional<Employee> employees = employeeRepository.findByInitials("PEDO");
        assertNotNull(employees);
        assertEquals("PEDO", employees.get().getInitials());
    }

    @Test
    public void testGetAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);
        assertEquals(14, employees.size());
    }
}
