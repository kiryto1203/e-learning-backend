package com.elearningbackend.service;

import com.elearningbackend.dto.QuestionDto;

import java.util.List;

public interface ILessionService {
    List<QuestionDto> generateLession(String subcategoryCode);
}
