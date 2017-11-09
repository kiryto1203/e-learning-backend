package com.elearningbackend.repository;

import com.elearningbackend.entity.SystemResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISystemResultRepository extends CrudRepository<SystemResult, Integer> {
}
