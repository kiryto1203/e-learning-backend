package com.elearningbackend.repository;

import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.entity.SystemResultId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemResultRepository extends JpaRepository<SystemResult, SystemResultId>{
}
