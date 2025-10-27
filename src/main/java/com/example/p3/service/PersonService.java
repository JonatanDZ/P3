package com.example.p3.service;

import com.example.p3.model.Person;
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
public class PersonService {
    private final Map<Long, Person> inMemoryDb = new ConcurrentHashMap<>();

    static long counter = 0;
    public long useCounter() {
        return ++counter;
    }

    @PostConstruct
    public void seedData() {
        JsonParserPerson("src/main/resources/static/Person_MockData.json");
        // --- Mock data for development ---
    }

    public Map<Long, Person> getAllPersons() {
        return inMemoryDb;
    }
//Uses .equals since we simple compare enum values
    public Map<Long, Person> getAllPersonsByDepartment(Person.Department department) {
        return getAllPersons().entrySet().stream()
                .filter(entry -> entry.getValue().getDepartment().equals(department))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Array.asList makes id into a list of longs making the .contains posible to use
    public Map<Long, Person> getPersonById(long id) {
        return getAllPersons().entrySet().stream()
                .filter(entry -> Arrays.asList(entry.getValue().getId()).contains(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Til at søge efter
    public Map<Long, Person> getPersonByInitials(String initials) {
        return getAllPersons().entrySet().stream()
                .filter(entry -> entry.getValue().getInitials().contains(initials))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Til at søge efter
    public Map<Long, Person> getPersonByEmail(String email) {
        return getAllPersons().entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().contains(email))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
//Til at søge efter (ikke case sensitive) possible returns multiple
    public Map<Long, Person> getPersonByName(String name) {
        return getAllPersons().entrySet().stream()
                .filter(entry -> entry.getValue().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /* Potentielt samlet funtion?
    public Map<Long, Person> getPersonInfo(String infoType, String info) {
        return switch (infoType) {
            case "id" -> getAllPersons().entrySet().stream()
                    .filter(entry -> Arrays.asList(entry.getValue().getId()).contains(info))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "initials" -> getAllPersons().entrySet().stream()
                    .filter(entry -> entry.getValue().getInitials().contains(info))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "email" -> getAllPersons().entrySet().stream()
                    .filter(entry -> entry.getValue().getEmail().contains(info))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case "name" -> getAllPersons().entrySet().stream()
                    .filter(entry -> entry.getValue().getName().toLowerCase().contains(info.toLowerCase()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            default -> new HashMap<>();
        };
    }
     */

    /// /////////////Mock database omformer/////////////// ///
    public void JsonParserPerson(String src) {
        //https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html
        ObjectMapper mapper = new ObjectMapper();

        //We are creating an empty list to fill up the with the json data
        Person[] persons;

        // I needed to make a try/catch otherwise it complained.
        try {
            //Reads the file that is provided and fits it to how Person looks
            persons = mapper.readValue(new File(src), Person[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //We are posting all the elements to the DB.
        for (Person person : persons) {
            inMemoryDb.put(useCounter(), person);
        }
    }



}