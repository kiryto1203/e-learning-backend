package com.elearningbackend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "lesson", catalog = "e_learning")
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;
    private String lessonCode;
    private User user;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private Integer isFinish;
    private Set<LessonReport> mappedLessonReports = new TreeSet<>();

    public Lesson() {
    }

    public Lesson(String lessonCode, User user) {
        this.lessonCode = lessonCode;
        this.user = user;
    }

    public Lesson(String lessonCode, User user, Timestamp creationDate, Timestamp lastUpdateDate,
            Set<LessonReport> mappedLessonReports) {
        this.lessonCode = lessonCode;
        this.user = user;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.mappedLessonReports = mappedLessonReports;
    }

    @Id
    @Column(name = "lesson_code", unique = true, nullable = false, length = 100)
    public String getLessonCode() {
        return this.lessonCode;
    }

    public void setLessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }

    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "last_update_date")
    public Timestamp getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @ManyToOne
    @JoinColumn(name = "lesson_username", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "is_finish")
    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    @OneToMany(mappedBy = "mappedLesson")
    public Set<LessonReport> getMappedLessonReports() {
        return mappedLessonReports;
    }

    public void setMappedLessonReports(Set<LessonReport> mappedLessonReports) {
        this.mappedLessonReports = mappedLessonReports;
    }
}
