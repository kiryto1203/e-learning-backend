package com.elearningbackend.repository;

import com.elearningbackend.entity.AnswerBank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerBankRepository extends CrudRepository<AnswerBank, Integer>{
}