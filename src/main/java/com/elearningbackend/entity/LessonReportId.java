package com.elearningbackend.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class LessonReportId implements Serializable {

    private static final long serialVersionUID = 1L;
    private String lessonReportLessonCode;
    private String lessonReportQuestionCode;

    public LessonReportId() {
    }

    public LessonReportId(String lessonReportLessonCode, String lessonReportQuestionCode) {
        this.lessonReportLessonCode = lessonReportLessonCode;
        this.lessonReportQuestionCode = lessonReportQuestionCode;
    }

    @Column(name = "lesson_report_lesson_code", nullable = false, length = 100)
    public String getLessonReportLessonCode() {
        return lessonReportLessonCode;
    }

    public void setLessonReportLessonCode(String lessonReportLessonCode) {
        this.lessonReportLessonCode = lessonReportLessonCode;
    }

    @Column(name = "lesson_report_question_code", nullable = false, length = 100)
    public String getLessonReportQuestionCode() {
        return lessonReportQuestionCode;
    }

    public void setLessonReportQuestionCode(String lessonReportQuestionCode) {
        this.lessonReportQuestionCode = lessonReportQuestionCode;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LessonReportId that = (LessonReportId) o;

        if (lessonReportLessonCode != null ?
                !lessonReportLessonCode.equals(that.lessonReportLessonCode) :
                that.lessonReportLessonCode != null)
            return false;
        if (lessonReportQuestionCode != null ?
                !lessonReportQuestionCode.equals(that.lessonReportQuestionCode) :
                that.lessonReportQuestionCode != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = lessonReportLessonCode != null ? lessonReportLessonCode.hashCode() : 0;
        result = 31 * result + (lessonReportQuestionCode != null ? lessonReportQuestionCode.hashCode() : 0);
        return result;
    }
}
