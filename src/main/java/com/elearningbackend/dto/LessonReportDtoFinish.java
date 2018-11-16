package com.elearningbackend.dto;

import com.elearningbackend.entity.LessonReportId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessonReportDtoFinish implements Comparable<LessonReportDtoFinish>{
    private LessonReportId lessonReportId;
    private String questionContent;
    private int questionType;
    private String questionParentCode;
    private String subcategoryCode;
    private Double questionPoint;
    private List<AnswerDto> userAnswers;
    @JsonIgnore
    private LessonDto mappedLessonDto;
    private Double userPoint;

    private List<AnswerDto> correctAnswers;
    private List<AnswerDto> incorrectAnswers;

    @Override
    public int compareTo(LessonReportDtoFinish o) {
        return this.getLessonReportId().getLessonReportQuestionCode().compareTo(o.getLessonReportId().getLessonReportQuestionCode());
    }
}
