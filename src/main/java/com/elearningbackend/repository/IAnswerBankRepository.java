package com.elearningbackend.repository;

import com.elearningbackend.entity.AnswerBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerBankRepository extends JpaRepository<AnswerBank, String> {
}
