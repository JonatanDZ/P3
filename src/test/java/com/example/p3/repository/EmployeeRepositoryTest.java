package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
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

    //This test shows that our findByInitials database call is case-insensitive, making sure that it finds the same employye
    //with upper or lower case letters
    @Test
    public void testFindByLowerCaseInitials(){
        Optional<Employee> employees = employeeRepository.findByInitials("pedo");
        assertNotNull(employees);
        assertTrue(employees.isPresent());
    }

    //This test will look for an employee that is not present and expect the returned employees to be empty
    @Test
    public void testFindByInitialsNotPresent(){
        Optional<Employee> employees = employeeRepository.findByInitials("KOLO");
        //Assert true that the employee returned is empty, as there is no employee with initials KOLO
        assertTrue(employees.isEmpty(), "Employee not found");
    }

    //This test looks into the actual database and finds all employees
    @Test
    public void testGetAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);
        assertEquals(14, employees.size());
    }

    //This tests that there will not be dublicate employees in the database with different instances
    @Test
    public void testDublicateEmployee() {
        //created to pass a department to newly created employees
        Department department = new Department(1, "Test", true);
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepository.save(e1);

        Employee e2 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepository.save(e2);
        //Expect 15 employees as there are 14 in the database, and though two different employees in the test are created,
        //only one should be added as they has the same initials, and therefore when saving e2 it will only update the name and email
        assertEquals(15, employeeRepository.count());
    }

    //This tests that we can update an employee by saving a new employee with the same initials
    @Test
    public void testUpdateEmployee(){
        Department department = new Department(1, "Test", true);
        Employee e1 = new Employee("MOJO", "Morten Johansen", "MOJO@mail.dk", department);
        employeeRepository.save(e1);
        //When creating an employee with the same initials, it will update an existing instead of creating a new class
        Employee e2 = new Employee("MOJO", "Molo Kolo", "MOJO@mail.dk", department);
        employeeRepository.save(e2);


        Optional<Employee> employees = employeeRepository.findByInitials("MOJO");
        //First assert that the employee is present
        assertTrue(employees.isPresent());
        Employee updatedEmployee = employees.get();
        //Here we assert that the name of the employee is Molo Kolo and not Morten Johansen
        assertEquals("Molo Kolo", updatedEmployee.getName());
    }

}
