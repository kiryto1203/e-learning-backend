package com.elearningbackend.repository;

import com.elearningbackend.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, String> {
    @Query("select a from Lesson a where a.isFinish = ?1")
    Page<Lesson> findAllByIsFinish(int isFinish, Pageable pageable);
    Lesson findTopByIsFinishOrderByCreationDateDesc(int isFinish);
}
