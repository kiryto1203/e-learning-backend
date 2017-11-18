package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AnswerBankDto {
    private String answerCode;
    private String answerContent;
    private Date creationDate;
    private Date lastUpdateDate;
    private String creatorUsername;
    private String lastUpdaterUsername;

    public AnswerBankDto(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
