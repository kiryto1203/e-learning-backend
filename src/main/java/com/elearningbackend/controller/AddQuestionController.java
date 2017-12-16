package com.elearningbackend.controller;

import com.elearningbackend.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AddQuestionController {

    @GetMapping("/addquestion")
    public AddQuestionDto add(){
        AddQuestionDto addQuestionDto = new AddQuestionDto();
        addQuestionDto.setQuestionBankDto(new QuestionBankDto());
        List<AddAnswerDto> addAnswerDtos = new ArrayList();
        AddAnswerDto addAnswerDto = new AddAnswerDto();
        addAnswerDto.setAnswerBankDto(new AnswerBankDto());
        addAnswerDto.setSystemResultDto(new SystemResultDto());
        addAnswerDtos.add(addAnswerDto);
        addQuestionDto.setAnswers(addAnswerDtos);
        return  addQuestionDto;
    }
}
