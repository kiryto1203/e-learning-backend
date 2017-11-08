/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.dto;

import com.elearningbackend.entity.QuestionLession;
import com.elearningbackend.entity.SystemResult;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author c1508l3694
 */
public class QuestionBankDto {
    private String questionCode;
    private int questionType;
    private String questionContent;
    private String questionParentCode;
    private Date creationDate;
    private Date lastUpdateDate;
    private String creatorUsername;
    private String lastUpdaterUsername;
    private SystemResult systemResult;
    private Set<QuestionLession> questionLessions = new HashSet<QuestionLession>(0);

    public QuestionBankDto() {
    }

    public QuestionBankDto(String questionCode, String questionContent) {
        this.questionCode = questionCode;
        this.questionContent = questionContent;
    }

    public QuestionBankDto(String questionCode, int questionType, String questionContent, String questionParentCode, Date creationDate, Date lastUpdateDate, String creatorUsername, String lastUpdaterUsername, SystemResult systemResult) {
        this.questionCode = questionCode;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.questionParentCode = questionParentCode;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.creatorUsername = creatorUsername;
        this.lastUpdaterUsername = lastUpdaterUsername;
        this.systemResult = systemResult;
    }
    
    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionParentCode() {
        return questionParentCode;
    }

    public void setQuestionParentCode(String questionParentCode) {
        this.questionParentCode = questionParentCode;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getLastUpdaterUsername() {
        return lastUpdaterUsername;
    }

    public void setLastUpdaterUsername(String lastUpdaterUsername) {
        this.lastUpdaterUsername = lastUpdaterUsername;
    }

    public SystemResult getSystemResult() {
        return systemResult;
    }

    public void setSystemResult(SystemResult systemResult) {
        this.systemResult = systemResult;
    }

    public Set<QuestionLession> getQuestionLessions() {
        return questionLessions;
    }

    public void setQuestionLessions(Set<QuestionLession> questionLessions) {
        this.questionLessions = questionLessions;
    }

}
