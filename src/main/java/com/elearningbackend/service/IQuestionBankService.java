/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;

public interface IQuestionBankService {
    Pager<QuestionBankDto> loadAllQuestions(int currentPage, int noOfRowInPage);
    QuestionBankDto getQuestionByCode(String questionCode);
    Pager<QuestionBankDto> getQuestionByCreator(String creatorUsername, int currentPage, int noOfRowInPage);
    QuestionBankDto addNewQuestion(QuestionBankDto question) throws Exception;
    QuestionBankDto editQuestion(QuestionBankDto question) throws Exception;
    QuestionBankDto deleteQuestion(String questionCode) throws Exception;
}
