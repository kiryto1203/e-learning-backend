/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.repository.IQuestionBankRepository;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
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
public class QuestionBankService extends AbstractCustomService<QuestionBankDto, String, QuestionBank> {

    @Autowired
    public QuestionBankService(IQuestionBankRepository repository) {
        super(repository, new Paginator<>(QuestionBankDto.class));
    }

    @Override
    public Pager<QuestionBankDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<QuestionBank> pager = getQuestionRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage,
                ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public QuestionBankDto getOneByKey(String questionCode) throws ElearningException {
        QuestionBank question = getQuestionRepository().findOne(questionCode);
        if (question == null) {
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        }
        return mapper.map(question, QuestionBankDto.class);
    }

    @Override
    public Pager<QuestionBankDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        Page<QuestionBank> pager = getQuestionRepository().fetchQuestionByCreator(
                creatorUsername, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public QuestionBankDto add(QuestionBankDto question) throws ElearningException {
        try {
            saveQuestion(question);
            return question;
        } catch (Exception e){
            //TODO
            throw new ElearningException("Cannot add new question");
        }
    }

    @Override
    public QuestionBankDto edit(QuestionBankDto question) throws ElearningException {
        QuestionBankDto questionByCode = getOneByKey(question.getQuestionCode());
        if (questionByCode != null) {
            saveQuestion(question);
            return question;
        }
        //TODO
        throw new ElearningException("Cannot edit question");
    }

    @Override
    public QuestionBankDto delete(String questionCode) throws ElearningException {
        QuestionBankDto questionByCode = getOneByKey(questionCode);
        if (questionByCode != null) {
            getQuestionRepository().delete(mapper.map(questionByCode, QuestionBank.class));
            return questionByCode;
        }
        //TODO
        throw new ElearningException("Cannot delete question");
    }

    public Pager<QuestionBankDto> getBySubcategoryCode(String subcategoryCode, int currentPage, int noOfRowInPage) {
        Page<QuestionBank> pager = getQuestionRepository().fetchQuestionBySubcategoryCode(
            subcategoryCode, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    private void saveQuestion(QuestionBankDto question) {
        getQuestionRepository().save(mapper.map(question, QuestionBank.class));
    }

    private IQuestionBankRepository getQuestionRepository() {
        return (IQuestionBankRepository) getRepository();
    }
}
