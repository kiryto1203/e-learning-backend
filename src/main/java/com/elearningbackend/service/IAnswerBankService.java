/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;

public interface IAnswerBankService {
    Pager<AnswerBankDto> loadAllAnswers(int currentPage, int noOfRowInPage);
    AnswerBankDto getAnswerByCode(String answerCode);
    Pager<AnswerBankDto> getAnswerByCreator(String creatorUsername, int currentPage, int noOfRowInPage);
    AnswerBankDto addNewAnswer(AnswerBankDto answer) throws Exception;
    AnswerBankDto editAnswer(AnswerBankDto answer) throws Exception;
    AnswerBankDto deleteAnswer(String answerCode) throws Exception;
}
