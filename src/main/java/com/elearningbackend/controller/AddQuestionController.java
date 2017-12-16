package com.elearningbackend.controller;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.*;
import com.elearningbackend.entity.SystemResultId;
import com.elearningbackend.service.AbstractCommonService;
import com.elearningbackend.utility.ResultCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AddQuestionController extends BaseController{

    @Autowired
    private AbstractCommonService<AddQuestionDto,SystemResultId> addQuestionService;

    @GetMapping("/addquestion")
    public List<AddQuestionDto> add(){
        AddQuestionDto addQuestionDto = new AddQuestionDto();
        addQuestionDto.setQuestionBankDto(new QuestionBankDto());
        List<AddAnswerDto> addAnswerDtos = new ArrayList();
        AddAnswerDto addAnswerDto = new AddAnswerDto();
        addAnswerDto.setAnswerBankDto(new AnswerBankDto());
        addAnswerDto.setSystemResultDto(new SystemResultDto());
        addAnswerDtos.add(addAnswerDto);
        addQuestionDto.setAnswers(addAnswerDtos);
        List<AddQuestionDto> list = Arrays.asList(addQuestionDto);
        return  list;
    }

    @PostMapping("/addquestions")
    public Result<List<AddQuestionDto>> add(@Valid @RequestBody List<AddQuestionDto> addQuestionDtos){
        /**
         * set current user
         */
        CurrentUser currentUser = getCurrentUser();
        try {
            addQuestionService.add(addQuestionDtos,currentUser.getUsername());
            return new Result(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(),null);
        } catch (ElearningException e) {
            e.printStackTrace();
            return new Result(e.getErrorCode(),
                    e.getMessage(),null);
        }
    }
}
