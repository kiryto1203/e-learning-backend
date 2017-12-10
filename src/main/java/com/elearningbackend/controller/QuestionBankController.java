package com.elearningbackend.controller;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.SortingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class QuestionBankController {
    @Autowired
    @Qualifier("questionBankService")
    private IAbstractService<QuestionBankDto, String> abstractService;

    @GetMapping("/questions")
    public Pager<QuestionBankDto> loadAll(
            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
            @RequestParam(defaultValue = SortingConstants.SORT_QUESTION_DEFAULT_FIELD) String sortBy,
            @RequestParam(defaultValue = SortingConstants.ASC) String direction){
       return abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
    }

    //TODO
    @GetMapping("/questions/{key}")
    public QuestionBankDto getOne(@PathVariable("key") String key){
        return null;
    }

    //TODO
    @GetMapping("/questions/{creatorUsername}")
    public Pager<QuestionBankDto> getByCreator(
            @PathVariable("creatorUsername") String creatorUsername,
            @RequestParam("page") int page,
            @RequestParam("noOfRowInPage") int noOfRowInPage
    ){
        return null;
    }

    //TODO
    @PostMapping("/questions")
    public String add(@RequestBody QuestionBankDto questionBankDto){
        return null;
    }

    //TODO
    @PutMapping("/questions/{key}")
    public String edit(@PathVariable String key, @RequestBody QuestionBankDto questionBankDto){
        return null;
    }

    //TODO
    @DeleteMapping("/questions/{key}")
    public String delete(@PathVariable String key){
        return null;
    }
}
