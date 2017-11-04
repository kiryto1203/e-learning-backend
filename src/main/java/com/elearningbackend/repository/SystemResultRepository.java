package com.elearningbackend.repository;

import com.elearningbackend.entity.SystemResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface SystemResultRepository extends CrudRepository<SystemResult, Integer> {
}
