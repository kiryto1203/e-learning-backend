package com.elearningbackend.dto;

import com.elearningbackend.entity.Lession;
import com.elearningbackend.entity.LessionReportId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessionReportDto {
    private LessionReportId lessionReportId;
    private String questionContent;
    private String questionParentCode;
    private String subcategoryCode;
    private Double questionPoint;
    private List<String> incorrectAnswers;
    private List<String> correctAnswers;

    private Double userPoint;
    private String userAnswer;
    private Lession mappedLession;
}
