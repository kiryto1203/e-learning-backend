package com.elearningbackend.dto;

import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.entity.SystemResultId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SystemResultDto {

    @NotBlank(message = "System result id cannot be empty!")
    @JsonProperty("system_result_id")
    private SystemResultId systemResultId;

    @JsonProperty("question_bank")
    private QuestionBank questionBank;

    @JsonProperty("answer_bank")
    private AnswerBank answerBank;

    @JsonProperty("system_result_position")
    private int systemResultPosition;

    @JsonProperty("system_result_is_correct")
    private int systemResultIsCorrect;

    @JsonProperty("creation_date")
    private Timestamp creationDate;

    @JsonProperty("last_update_date")
    private Timestamp lastUpdateDate;

    @JsonProperty("last_update_user_name")
    private String lastUpdaterUsername;

}
