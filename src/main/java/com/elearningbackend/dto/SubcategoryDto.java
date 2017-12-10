package com.elearningbackend.dto;

import com.elearningbackend.entity.Category;
import com.elearningbackend.entity.QuestionBank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
public class SubcategoryDto {
    @JsonProperty("subcategory_code")
    private String subcategoryCode;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("category_code")
    private String categoryCode;
    @JsonProperty("subcategory_introduction")
    private String subcategoryIntro;
    @JsonProperty("creation_date")
    private Timestamp creationDate;
    @JsonProperty("last_update_date")
    private Timestamp lastUpdateDate;
    @JsonProperty("questions")
    private Set<QuestionBank> questionBanks;

    public SubcategoryDto(String subcategoryCode, Timestamp creationDate, Timestamp lastUpdateDate) {
        this.subcategoryCode = subcategoryCode;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
