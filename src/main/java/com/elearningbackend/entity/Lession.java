package com.elearningbackend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lession", catalog = "e_learning")
public class Lession implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String lessionCode;
    private User user;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private Set<QuestionLession> questionLessions = new HashSet<QuestionLession>(0);

    public Lession() {
    }

    public Lession(String lessionCode, User user) {
        this.lessionCode = lessionCode;
        this.user = user;
    }

    public Lession(String lessionCode, User user, Timestamp creationDate, Timestamp lastUpdateDate,
            Set<QuestionLession> questionLessions) {
        this.lessionCode = lessionCode;
        this.user = user;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.questionLessions = questionLessions;
    }

    @Id
    @Column(name = "lession_code", unique = true, nullable = false, length = 100)
    public String getLessionCode() {
        return this.lessionCode;
    }

    public void setLessionCode(String lessionCode) {
        this.lessionCode = lessionCode;
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

    @OneToMany(mappedBy = "lession", cascade= CascadeType.ALL)
    public Set<QuestionLession> getQuestionLessions() {
        return this.questionLessions;
    }

    public void setQuestionLessions(Set<QuestionLession> questionLessions) {
        this.questionLessions = questionLessions;
    }

    @ManyToOne
    @JoinColumn(name = "lession_username", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
