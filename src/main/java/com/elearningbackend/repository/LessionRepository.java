package com.elearningbackend.repository;

import com.elearningbackend.entity.Lession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface LessionRepository extends CrudRepository<Lession, String>{
}
