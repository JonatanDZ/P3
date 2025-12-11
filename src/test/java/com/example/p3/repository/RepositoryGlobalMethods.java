package com.example.p3.repository;

import com.example.p3.entities.Department;
import com.example.p3.entities.Employee;
import com.example.p3.entities.Jurisdiction;
import com.example.p3.entities.Stage;
import com.example.p3.repositories.DepartmentRepository;
import com.example.p3.repositories.JurisdictionRepository;
import com.example.p3.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class RepositoryGlobalMethods {
    @Autowired
    private StageRepository stageRepositoryTest;

    @Autowired
    private DepartmentRepository departmentRepositoryTest;

    @Autowired
    private JurisdictionRepository jurisdictionRepositoryTest;

//    @Autowired
//    private StageRepository stageRepositoryTest;

    public Employee createEmployee() {
        Department department = createDepartment();
        return new Employee("HOHO", "Holly Hobler", "HOHO@mail.dk", department);
    }

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
    public Stage createStage () {
        Stage stage = new Stage();
        stage.setName("testStage");
        return stageRepositoryTest.save(stage);
    }
}

