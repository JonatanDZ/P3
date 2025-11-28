package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.repositories.DepartmentRepository;
import com.example.p3.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest extends RepositoryGlobalMethods{

    @Autowired
    private EmployeeRepository employeeRepositoryTest;

    //Tests that an employee can be found by initials
    @Test
    public void testFindByInitials(){
        Department department = createDepartment();
        //Creates employee to test
        Employee e1 = new Employee("PEDO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e1);
        Optional<Employee> employees = employeeRepositoryTest.findByInitials("PEDO");
        assertNotNull(employees); //Checks if the employee is null
        assertTrue(employees.isPresent()); //Check that employee with the initial is in the mock database
    }

    //This test shows that our findByInitials database call is case-insensitive, making sure that it finds the same employee
    //with upper or lower case letters
    @Test
    public void testFindByLowerCaseInitials(){
        Department department = createDepartment();
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e1);

        Optional<Employee> employees = employeeRepositoryTest.findByInitials("hoho");
        assertNotNull(employees);
        assertTrue(employees.isPresent()); //Check that employee with the initial(regardless of case) is in the mock database
    }

    //This test will look for an employee that is not present and expect the returned employees to be empty
    @Test
    public void testFindByInitialsNotPresent(){
        //Finds initials which are not i the database
        Optional<Employee> employees = employeeRepositoryTest.findByInitials("KOLO");
        //Assert true that the employee returned is empty, as there is no employee with initials KOLO
        assertTrue(employees.isEmpty(), "Employee not found");
    }

    //We populate the database with 5 employees, and the test should return a list with the size 5
    @Test
    public void testGetAllEmployees(){
        Department department = createDepartment();
        //Creates 5 mock employees
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        Employee e2 = new Employee("MOMO", "Morten Moller", "MOMO@mail.dk", department);
        Employee e3 = new Employee("SISI", "Signe Simonsen", "SISI@mail.dk", department);
        Employee e4 = new Employee("LALA", "Lars Larsen", "LALA@mail.dk", department);
        Employee e5 = new Employee("KOKO", "Kasper Kokholm", "KOKO@mail.dk", department);

        //Saves mock employees in the mock database
        employeeRepositoryTest.save(e1);
        employeeRepositoryTest.save(e2);
        employeeRepositoryTest.save(e3);
        employeeRepositoryTest.save(e4);
        employeeRepositoryTest.save(e5);

        //Makes a list of employees
        List<Employee> employees = employeeRepositoryTest.findAll();
        assertNotNull(employees);
        assertEquals(5, employees.size()); //Check if the employee amount and list size are the same
    }

    //This tests that there will not be duplicate employees in the database with different instances
    @Test
    public void testDuplicateEmployee() {
        //created to pass a department to newly created employees
        Department department = createDepartment();
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e1);

        //creates an employee with the same information (most important initials), to check if it is discarded
        Employee e2 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e2);

        //Check to se that the last added employee isn't added
        assertEquals(1, employeeRepositoryTest.count());
    }

    //Test if an employee can be updated when a new employee is saved with the same initials
    @Test
    public void testUpdateEmployee(){
        Department department = createDepartment();
        Employee e1 = new Employee("MOJO", "Morten Johansen", "MOJO@mail.dk", department);
        employeeRepositoryTest.save(e1);
        //When creating an employee with the same initials, it will update an existing instead of creating a new class
        Employee e2 = new Employee("MOJO", "Molo Kolo", "MOJO@mail.dk", department);
        employeeRepositoryTest.save(e2);


        Optional<Employee> employees = employeeRepositoryTest.findByInitials("MOJO");
        //First assert that the employee is present
        assertTrue(employees.isPresent());
        Employee updatedEmployee = employees.get();
        //Here we assert that the name of the employee is Molo Kolo and not Morten Johansen
        assertEquals("Molo Kolo", updatedEmployee.getName());
    }
}
