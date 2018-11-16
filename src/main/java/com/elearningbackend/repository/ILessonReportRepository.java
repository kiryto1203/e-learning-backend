package com.elearningbackend.repository;

import com.elearningbackend.entity.LessonReport;
import com.elearningbackend.entity.LessonReportId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dohalong on 22/12/2017.
 */
public interface ILessonReportRepository extends JpaRepository<LessonReport, LessonReportId>{
}
