package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class AnswerBankDto {
    private String answerCode;
    private String answerContent;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private String creatorUsername;
    private String lastUpdaterUsername;

    public AnswerBankDto(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}
