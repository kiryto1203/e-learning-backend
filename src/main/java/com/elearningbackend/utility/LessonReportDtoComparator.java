package com.elearningbackend.utility;

import com.elearningbackend.dto.LessonReportDto;

import java.util.Comparator;

/**
 * Created by dohalong on 23/12/2017.
 */
public class LessonReportDtoComparator implements Comparator<LessonReportDto> {
    @Override
    public int compare(LessonReportDto o1, LessonReportDto o2) {
        return o1.getLessonReportId().getLessonReportQuestionCode().compareTo(o2.getLessonReportId().getLessonReportQuestionCode());
    }
}
