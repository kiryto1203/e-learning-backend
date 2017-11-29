package com.elearningbackend.controller;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.service.IAbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api")
public class QuestionBankController {
    @Autowired
    @Qualifier("questionBankService")
    private IAbstractService<QuestionBankDto, String> abstractService;

    @GetMapping("/questions")
    public Pager<QuestionBankDto> loadAll(
            @RequestParam("page") int page,
            @RequestParam("noOfRowInPage") int noOfRowInPage){
        return null;
    }

    @GetMapping("/questions/{key}")
    public QuestionBankDto getOne(@PathVariable("key") String key){
        return null;
    }

    @GetMapping("/questions/{creatorUsername}")
    public Pager<QuestionBankDto> getByCreator(
            @PathVariable("creatorUsername") String creatorUsername,
            @RequestParam("page") int page,
            @RequestParam("noOfRowInPage") int noOfRowInPage
    ){
        return null;
    }

    @PostMapping("/questions")
    public String add(@RequestBody QuestionBankDto questionBankDto){
        return null;
    }

    @PutMapping("/questions/{key}")
    public String edit(@PathVariable String key, @RequestBody QuestionBankDto questionBankDto){
        return null;
    }

    @DeleteMapping("/questions/{key}")
    public String delete(@PathVariable String key){
        return null;
    }
}
