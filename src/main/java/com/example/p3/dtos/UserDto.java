package com.example.p3.dtos;

import com.example.p3.model.User;

public class UserDto {
    public User createUser(String spaceId,
                           String id,
                           String initials,
                           String name,
                           String email,
                           User.Department[] department)

    {
        User createdUser = new User();
        createdUser.setId(useCounter());
        createdUser.setName(name);
        createdUser.setInitials(initials);
        createdUser.setEmail(email);
        createdUser.setDepartment(department);

        // Store the object createdtool in the Hash map (inMemoryDb), and use its ID (createdtool.getId()) as the key.
        inMemoryDb.put(createdUser.getId(), createdUser);
        return createdUser;
    }
}
