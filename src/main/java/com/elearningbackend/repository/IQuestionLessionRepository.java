package com.elearningbackend.repository;

import com.elearningbackend.entity.QuestionLession;
import com.elearningbackend.entity.QuestionLessionId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionLessionRepository extends CrudRepository<QuestionLession, QuestionLessionId> {
}
