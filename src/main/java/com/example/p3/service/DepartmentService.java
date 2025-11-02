package com.example.p3.service;

import com.example.p3.entities.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DepartmentService {

    // hashmap to be made her
    // in memory database:
    private final Map<Long, Department> inMemoryDb = new ConcurrentHashMap<>();

    static long counter = 0;
    public long useCounter() {
        return ++counter;
    }

    @PostConstruct
    public void seedData() {
        JsonParserDepartment("src/main/resources/static/DEPARTMENT_MOCK_DATA.json");
        // --- Mock data for development ---

    }

    public Map<Long, Department> getAllDepartments() {
        System.out.println(inMemoryDb);
        return inMemoryDb;
    }

    public void JsonParserDepartment(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Department[] departments;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how tool looks
            departments = mapper.readValue(new File(src), Department[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for(Department department : departments){
            inMemoryDb.put(useCounter(), department);
        }
    }
}
