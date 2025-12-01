package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.repositories.DepartmentRepository;
import com.example.p3.repositories.EmployeeRepository;
import com.example.p3.repositories.JurisdictionRepository;
//import com.example.p3.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryGlobalMethods {
    @Autowired
    private DepartmentRepository departmentRepositoryTest;

    @Autowired
    private JurisdictionRepository jurisdictionRepositoryTest;

//    @Autowired
//    private StageRepository stageRepositoryTest;


    //We need to create a department, as the mock database is not populated
    public Department createDepartment() {
        Department department = new Department();
        department.setName("Test Department");
        department.setIs_dev(true);
        return departmentRepositoryTest.save(department);
    }

    public Jurisdiction createJurisdiction () {
        Jurisdiction jurisdiction = new Jurisdiction();
        jurisdiction.setName("testJurisdiction");
        return jurisdictionRepositoryTest.save(jurisdiction);
    }
/*
    public Stage createStage () {
        Stage stage = new Stage();
        stage.setName("testStage");
        return stageRepositoryTest.save(stage);
    }
*/
}

