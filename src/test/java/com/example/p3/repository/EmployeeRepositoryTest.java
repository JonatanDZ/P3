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
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepositoryTest;

    @Autowired
    private DepartmentRepository departmentRepositoryTest;

    //We need to create a department, as the mock database is not populated
    private Department createDepartment(){
        Department department = new Department();
        department.setName("Test Department");
        department.setIs_dev(true);
        return departmentRepositoryTest.save(department);
    }

    //This test looks into the actual database and tries to find the actual employee
    @Test
    public void testFindByInitials(){
        Department department = createDepartment();
        Employee e1 = new Employee("PEDO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e1);
        Optional<Employee> employees = employeeRepositoryTest.findByInitials("PEDO");
        assertNotNull(employees);
        assertTrue(employees.isPresent());
    }

    //This test shows that our findByInitials database call is case-insensitive, making sure that it finds the same employye
    //with upper or lower case letters
    @Test
    public void testFindByLowerCaseInitials(){
        Department department = createDepartment();
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e1);

        Optional<Employee> employees = employeeRepositoryTest.findByInitials("hoho");
        assertNotNull(employees);
        assertTrue(employees.isPresent());
    }

    //This test will look for an employee that is not present and expect the returned employees to be empty
    @Test
    public void testFindByInitialsNotPresent(){
        Optional<Employee> employees = employeeRepositoryTest.findByInitials("KOLO");
        //Assert true that the employee returned is empty, as there is no employee with initials KOLO
        assertTrue(employees.isEmpty(), "Employee not found");
    }

    //We populate the database with 5 employees, and the test should return that the
    @Test
    public void testGetAllEmployees(){
        Department department = createDepartment();
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        Employee e2 = new Employee("MOMO", "Morten Moller", "MOMO@mail.dk", department);
        Employee e3 = new Employee("SISI", "Signe Simonsen", "SISI@mail.dk", department);
        Employee e4 = new Employee("LALA", "Lars Larsen", "LALA@mail.dk", department);
        Employee e5 = new Employee("KOKO", "Kasper Kokholm", "KOKO@mail.dk", department);

        employeeRepositoryTest.save(e1);
        employeeRepositoryTest.save(e2);
        employeeRepositoryTest.save(e3);
        employeeRepositoryTest.save(e4);
        employeeRepositoryTest.save(e5);
        List<Employee> employees = employeeRepositoryTest.findAll();
        assertNotNull(employees);
        assertEquals(5, employees.size());
    }

    //This tests that there will not be duplicate employees in the database with different instances
    @Test
    public void testDuplicateEmployee() {
        //created to pass a department to newly created employees
        Department department = createDepartment();
        Employee e1 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e1);

        Employee e2 = new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
        employeeRepositoryTest.save(e2);
        //Expect 15 employees as there are 14 in the database, and though two different employees in the test are created,
        //only one should be added as they has the same initials, and therefore when saving e2 it will only update the name and email
        assertEquals(1, employeeRepositoryTest.count());
    }

    //This tests that we can update an employee by saving a new employee with the same initials
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
