package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.repository.IAnswerBankRepository;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AnswerBankService extends AbstractCustomService<AnswerBankDto, String, AnswerBank>{

    @Autowired
    public AnswerBankService(IAnswerBankRepository repository) {
        super(repository, new Paginator<>(AnswerBankDto.class));
    }

    @Override
    public Pager<AnswerBankDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<AnswerBank> pager = getAnswerRepository().findAll(Paginator.getValidPageRequest(currentPage, noOfRowInPage,
            ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public AnswerBankDto getOneByKey(String key) throws ElearningException {
        AnswerBank answer = getAnswerRepository().findOne(key);
        if (answer == null) {
            throw new ElearningException(Errors.USER_NOT_FOUND.getId(), Errors.USER_NOT_FOUND.getMessage());
        }
        return mapper.map(answer, AnswerBankDto.class);
    }

    @Override
    public Pager<AnswerBankDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        Page<AnswerBank> pager = getAnswerRepository().fetchAnswerByCreator(
            creatorUsername, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public AnswerBankDto add(AnswerBankDto answer) throws ElearningException {
        try {
            saveAnswer(answer);
            return answer;
        } catch (Exception e){
            //TODO
            throw new ElearningException("Cannot add new answer");
        }
    }

    @Override
    public AnswerBankDto edit(AnswerBankDto answer) throws ElearningException {
        AnswerBankDto answerByCode = getOneByKey(answer.getAnswerCode());
        if (answerByCode != null) {
            saveAnswer(answer);
            return answer;
        }
        //TODO
        throw new ElearningException("Cannot edit answer");
    }

    @Override
    public AnswerBankDto delete(String key) throws ElearningException {
        AnswerBankDto answerByCode = getOneByKey(key);
        if (answerByCode != null) {
            getAnswerRepository().delete(mapper.map(answerByCode, AnswerBank.class));
            return answerByCode;
        }
        //TODO
        throw new ElearningException("Cannot delete answer");
    }

    private IAnswerBankRepository getAnswerRepository() {
        return (IAnswerBankRepository) getRepository();
    }

    private void saveAnswer(AnswerBankDto answer) {
        getAnswerRepository().save(mapper.map(answer, AnswerBank.class));
    }
}
