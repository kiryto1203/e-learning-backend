package com.elearningbackend.controller;

import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.service.IAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AnswerBankController {
    @Autowired
    @Qualifier("answerBankService")
    private IAbstractService<AnswerBankDto, String> abstractService;

    @GetMapping("/answers")
    public Pager<AnswerBankDto> loadAll(
            @RequestParam("currentPage") int currentPage,
            @RequestParam("noOfRowInPage") int noOfRowInPage
    ){
        return null;
    }

    @GetMapping("/answers/{key}")
    public AnswerBankDto getByKey(@PathVariable("key") String key){
        return null;
    }

    @GetMapping("/answers/{creatorUsername}/")
    public Pager<AnswerBankDto> getByCreator(
            @PathVariable("creatorUsername") String creatorUserName,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("noOfRowInPage") int noOfRowInPage
    ){
        return null;
    }

    @PostMapping("/answers")
    public AnswerBankDto add(@RequestBody AnswerBankDto answerBankDto){
        return null;
    }

    @PutMapping("/answers/{key}")
    public AnswerBankDto edit(@PathVariable String key, @RequestBody AnswerBankDto answerBankDto){
        return null;
    }

    @DeleteMapping("/answers/{key}")
    public AnswerBankDto delete(@PathVariable("key") String key){
        return null;
    }
}
