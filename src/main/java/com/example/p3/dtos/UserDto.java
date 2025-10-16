package com.example.p3.dtos;

import com.example.p3.model.User;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String initials;
    private String name;
    private String email;
    private User.Department department;

    public UserDto(User u){
        this.id = u.getId();
        this.initials = u.getInitials();
        this.name = u.getName();
        this.email = u.getEmail();
        this.department = u.getDepartment();

    }
}
