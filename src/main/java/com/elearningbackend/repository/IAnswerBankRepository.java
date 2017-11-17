package com.elearningbackend.repository;

import com.elearningbackend.entity.AnswerBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerBankRepository extends JpaRepository<AnswerBank, String> {
    @Query("select a from AnswerBank a where a.creatorUsername like %?1%")
    Page<AnswerBank> fetchAnswerByCreator(String creatorUsername, Pageable pageable);
}
