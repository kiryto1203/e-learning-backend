package com.elearningbackend.repository;

import com.elearningbackend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, String> {
}
