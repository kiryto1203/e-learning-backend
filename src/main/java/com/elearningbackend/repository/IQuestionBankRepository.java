package com.elearningbackend.repository;

import com.elearningbackend.entity.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionBankRepository extends JpaRepository<QuestionBank, String> {
    @Query("select a from QuestionBank a where a.creatorUsername like %?1%")
    Page<QuestionBank> fetchQuestionByCreator(String creatorUsername, Pageable pageable);
}
