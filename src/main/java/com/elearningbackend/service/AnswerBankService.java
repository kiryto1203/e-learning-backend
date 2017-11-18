package com.elearningbackend.service;

import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.repository.IAnswerBankRepository;
import com.elearningbackend.utility.Paginator;
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
    public Pager<AnswerBankDto> loadAll(int currentPage, int noOfRowInPage) {
        Page<AnswerBank> pager = getAnswerRepository().findAll(new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public AnswerBankDto getOneByKey(String key) {
        AnswerBank answer = getAnswerRepository().findOne(key);
        return answer == null ? null : mapper.map(answer, AnswerBankDto.class);
    }

    @Override
    public Pager<AnswerBankDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        Page<AnswerBank> pager = getAnswerRepository().fetchAnswerByCreator(
            creatorUsername, new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, mapper);
    }

    @Override
    public AnswerBankDto add(AnswerBankDto answer) throws Exception {
        try {
            saveAnswer(answer);
            return answer;
        } catch (Exception e){
            throw new Exception("Cannot add new answer", e);
        }
    }

    @Override
    public AnswerBankDto edit(AnswerBankDto answer) throws Exception {
        AnswerBankDto answerByCode = getOneByKey(answer.getAnswerCode());
        if (answerByCode != null) {
            saveAnswer(answer);
            return answer;
        }
        // sang controller try catch -> throw http 200 + message
        throw new Exception("Cannot edit answer");
    }

    @Override
    public AnswerBankDto delete(String key) throws Exception {
        AnswerBankDto answerByCode = getOneByKey(key);
        if (answerByCode != null) {
            getAnswerRepository().delete(mapper.map(answerByCode, AnswerBank.class));
            return answerByCode;
        }
        // sang controller try catch -> throw http 200 + message
        throw new Exception("Cannot delete answer");
    }

    private IAnswerBankRepository getAnswerRepository() {
        return (IAnswerBankRepository) getRepository();
    }

    private void saveAnswer(AnswerBankDto answer) {
        getAnswerRepository().save(mapper.map(answer, AnswerBank.class));
    }
}
