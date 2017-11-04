package com.elearningbackend.repository;

import com.elearningbackend.entity.QuestionBank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface QuestionBankRepository extends CrudRepository<QuestionBank, String> {
}
