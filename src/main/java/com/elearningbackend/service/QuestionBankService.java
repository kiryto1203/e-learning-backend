/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.repository.IQuestionBankRepository;
import com.elearningbackend.utility.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author c1508l3694
 */
@Service
@Transactional
public class QuestionBankService extends AbstractService<QuestionBankDto, String, QuestionBank> {

    @Autowired
    public QuestionBankService(IQuestionBankRepository repository) {
        super(repository, new Paginator<>(QuestionBankDto.class));
    }

    @Override
    public Pager<QuestionBankDto> loadAll(int currentPage, int noOfRowInPage) {
        Page<QuestionBank> pager = getQuestionRepository().findAll(new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public QuestionBankDto getOneByKey(String questionCode) {
        QuestionBank question = getQuestionRepository().findOne(questionCode);
        return question == null ? null : mapper.map(question, QuestionBankDto.class);
    }

    @Override
    public Pager<QuestionBankDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        Page<QuestionBank> pager = getQuestionRepository().fetchQuestionByCreator(
                creatorUsername, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public QuestionBankDto add(QuestionBankDto question) throws Exception {
        try {
            saveQuestion(question);
            return question;
        } catch (Exception e){
            throw new Exception("Cannot add new question", e);
        }
    }

    @Override
    public QuestionBankDto edit(QuestionBankDto question) throws Exception {
        QuestionBankDto questionByCode = getOneByKey(question.getQuestionCode());
        if (questionByCode != null) {
            saveQuestion(question);
            return question;
        }
        // sang controller try catch -> throw http 200 + message
        throw new Exception("Cannot edit question");
    }

    @Override
    public QuestionBankDto delete(String questionCode) throws Exception {
        QuestionBankDto questionByCode = getOneByKey(questionCode);
        if (questionByCode != null) {
            getQuestionRepository().delete(mapper.map(questionByCode, QuestionBank.class));
            return questionByCode;
        }
        // sang controller try catch -> throw http 200 + message
        throw new Exception("Cannot delete question");
    }

    private void saveQuestion(QuestionBankDto question) {
        getQuestionRepository().save(mapper.map(question, QuestionBank.class));
    }

    private IQuestionBankRepository getQuestionRepository() {
        return (IQuestionBankRepository) getRepository();
    }
}
