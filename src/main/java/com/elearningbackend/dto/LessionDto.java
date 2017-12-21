package com.elearningbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class LessionDto {
    private String lessionCode;
    private String lessionUsername;
    private Timestamp creationDate;
    private Timestamp lastUpdateDate;
    private Integer isFinish;
    private Set<LessionReportDto> mappedLessionReports = new HashSet<>(0);
}
