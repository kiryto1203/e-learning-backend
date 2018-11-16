package com.elearningbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class LessonDto {
    private String lessonCode;
    @JsonIgnore
    private UserDto userDto;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private Integer isFinish;
    private Set<LessonReportDto> mappedLessonReports;
    private Set<LessonReportDtoFinish> mappedLessonReportsFinish;
    private Double totalPercent;
}
