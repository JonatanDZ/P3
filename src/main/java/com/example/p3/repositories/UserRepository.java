package com.example.p3.repositories;

import com.example.p3.controller.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
