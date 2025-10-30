package com.example.p3.service;

import com.example.p3.model.employee.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Long, Employee> inMemoryDb = new ConcurrentHashMap<>();

    static long counter = 0;
    public long useCounter() {
        return ++counter;
    }

    @PostConstruct
    public void seedData() {
        JsonParserEmployee("src/main/resources/static/Employee_MockData.json");
        // --- Mock data for development ---
    }

    public Map<Long, Employee> getAllEmployees() {
        return inMemoryDb;
    }
//Uses .equals since we simple compare enum values
    public Map<Long, Employee> getAllEmployeesByDepartment(Employee.Department department) {
        return getAllEmployees().entrySet().stream()
                .filter(entry -> entry.getValue().getDepartment().equals(department))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Array.asList makes id into a list of longs making the .contains posible to use
    public Map<Long, Employee> getEmployeeById(long id) {
        return getAllEmployees().entrySet().stream()
                .filter(entry -> Arrays.asList(entry.getValue().getId()).contains(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Til at søge efter
    public Map<Long, Employee> getEmployeeByInitials(String initials) {
        return getAllEmployees().entrySet().stream()
                .filter(entry -> entry.getValue().getInitials().contains(initials))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Til at søge efter
    public Map<Long, Employee> getEmployeeByEmail(String email) {
        return getAllEmployees().entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().contains(email))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Til at søge efter (ikke case sensitive) possible returns multiple
    public Map<Long, Employee> getEmployeeByName(String name) {
        return getAllEmployees().entrySet().stream()
                .filter(entry -> entry.getValue().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /* Potentielt samlet funtion?
    public Map<Long, Employee> getEmployeeInfo(String infoType, String info) {
        return switch (infoType) {
            case "id" -> getAllEmployees().entrySet().stream()
                    .filter(entry -> Arrays.asList(entry.getValue().getId()).contains(info))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "initials" -> getAllEmployees().entrySet().stream()
                    .filter(entry -> entry.getValue().getInitials().contains(info))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "email" -> getAllEmployees().entrySet().stream()
                    .filter(entry -> entry.getValue().getEmail().contains(info))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "name" -> getAllEmployees().entrySet().stream()
                    .filter(entry -> entry.getValue().getName().toLowerCase().contains(info.toLowerCase()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            default -> new HashMap<>();
        };
    }
     */

    /// /////////////Mock database omformer/////////////// ///
    public void JsonParserEmployee(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Employee[] employees;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how Employee looks
            employees = mapper.readValue(new File(src), Employee[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for (Employee employee : employees) {
            inMemoryDb.put(useCounter(), employee);
        }
    }



}