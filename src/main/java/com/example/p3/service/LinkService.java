package com.example.p3.service;


import com.example.p3.model.Link;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LinkService {

    // hashmap to be made her
    // in memory database:
    private final Map<Long, Link> inMemoryDb = new ConcurrentHashMap<>();
    private long counter = 1;
    public long useCounter() {
        return counter++;
    }

    //  Called automatically after Spring creates the service
    @PostConstruct
    public void seedData() {
        // --- Mock data for development ---
        Link l1 = new Link();
        l1.setId(useCounter());
        l1.setName("Spring Boot CORS Guide");
        l1.setUrl("https://spring.io/guides/gs/rest-service-cors");
        l1.setTags(new String[]{"spring", "cors", "frontend"});
        l1.setDepartments(new Link.Department[]{Link.Department.DEVOPS, Link.Department.FRONTEND});
        // If your Link has a Stage enum, set it here (safe to ignore if not present)
        l1.setStages(new Link.Stage[]{Link.Stage.DEVELOPMENT});
        inMemoryDb.put(l1.getId(), l1);

        Link l2 = new Link();
        l2.setId(useCounter());
        l2.setName("REST API Design Checklist");
        l2.setUrl("https://martinfowler.com/articles/richardsonMaturityModel.html");
        l2.setTags(new String[]{"rest", "design"});
        l2.setDepartments(new Link.Department[]{Link.Department.PAYMENTS, Link.Department.PROMOTIONS});
        l2.setStages(new Link.Stage[]{Link.Stage.PRODUCTION});
        inMemoryDb.put(l2.getId(), l2);

        Link l3 = new Link();
        l3.setId(useCounter());
        l3.setName("TypeScript Handbook");
        l3.setUrl("https://www.typescriptlang.org/docs/handbook/intro.html");
        l3.setTags(new String[]{"typescript", "frontend", "docs"});
        l3.setDepartments(new Link.Department[]{Link.Department.FRONTEND});
        l3.setStages(new Link.Stage[]{Link.Stage.STAGING});
        inMemoryDb.put(l3.getId(), l3);
    }


    // Min forståelse af isDynamic er den skal gemme på "brugerens" navn
    // Here we define how to create a link (The first string is for ID)
    public Link createLink(String spaceId,
                                     String name,
                                     String url,
                                     String tags,
                                     Link.Department[] department,
                                     Link.Stage[] stages,
                                     boolean isDynamic,
                                     String  createdBy)
    {
        Link createdlink = new Link();
        createdlink.setId(useCounter());
        createdlink.setName(name);
        createdlink.setUrl(url);
        createdlink.setTags(tags.split(",")); // [.... , ..... , .... , ..]
        createdlink.setDepartments(department);
        createdlink.setStages(stages);
        createdlink.setCreatedBy(createdBy);

        // Store the object createdlink in the Hash map (inMemoryDb), and use its ID (createdlink.getId()) as the key.
        inMemoryDb.put(createdlink.getId(), createdlink);
        return createdlink;
    }
    // CRUD methods
    public Map<Long, Link> getAllLinks() {
        return inMemoryDb;
    }
}


// Test
// {
//  "name": "Github",
//  "url": "https://github.com",
//  "tags": ["code", "repository"],
//  "departments": ["Development"],
//  "stages": ["Production"],
//  "isDynamic": true
//}