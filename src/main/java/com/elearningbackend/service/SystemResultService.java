package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.dto.SystemResultDto;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.entity.SystemResultId;
import com.elearningbackend.repository.ISystemResultRepository;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemResultService extends AbstractCustomService<SystemResultDto, SystemResultId, SystemResult> {

    @Autowired
    public SystemResultService(JpaRepository<SystemResult, SystemResultId> repository) {
        super(repository, new Paginator<>(SystemResultDto.class));
    }

    @Autowired
    @Qualifier("questionBankService")
    private IAbstractService<QuestionBankDto, String> abstractQuestionBankSercive;

    @Autowired
    @Qualifier("answerBankService")
    private AbstractCustomService<AnswerBankDto, String, AnswerBank> abstracAnswerBankService;


    @Override
    public Pager<SystemResultDto> getByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        return null;
    }

    @Override
    public Pager<SystemResultDto> loadAll(int currentPage, int noOfRowInPage, String sortBy, String direction) {
        Page<SystemResult> page = getSystemResultRepository().findAll(
                Paginator.getValidPageRequest(currentPage, noOfRowInPage, ServiceUtils.proceedSort(sortBy, direction)));
        return paginator.paginate(currentPage,page,noOfRowInPage,mapper);
    }

    @Override
    public SystemResultDto getOneByKey(SystemResultId key) throws ElearningException {
        SystemResult systemResult = getSystemResultRepository().findOne(key);
        if (systemResult == null){
            throw new ElearningException(Errors.SYSTEM_RESULT_NOT_EXITS.getId(),Errors.SYSTEM_RESULT_NOT_EXITS.getMessage());
        }
        return mapper.map(systemResult, SystemResultDto.class);
    }

    @Override
    public SystemResultDto add(SystemResultDto object) throws ElearningException {
        checkQuestionAndAnswerCode(object);
        SystemResultId systemResultId = getSystemResultId(object);
        if (getOneByKey(systemResultId) != null)
            throw new ElearningException(Errors.SYSTEM_RESULT_ID_EXIST.getId(),
                    Errors.SYSTEM_RESULT_ID_EXIST.getMessage());
        saveSystemResult(object);
        return object;
    }

    @Override
    public SystemResultDto edit(SystemResultDto object) throws ElearningException {
        checkQuestionAndAnswerCode(object);
        SystemResultId systemResultId = getSystemResultId(object);
        getOneByKey(systemResultId);
        saveSystemResult(object);
        return object;
    }

    @Override
    public SystemResultDto delete(SystemResultId key) throws ElearningException {
        SystemResultId systemResultId = new SystemResultId(key.getSystemResultQuestionCode(),
                key.getSystemResultAnswerCode());
        SystemResultDto systemResultDto = getOneByKey(systemResultId);
        getSystemResultRepository().delete(key);
        return systemResultDto;
    }

    private ISystemResultRepository getSystemResultRepository() {
        return (ISystemResultRepository) getRepository();
    }

    private void saveSystemResult(SystemResultDto systemResultDto){
        getSystemResultRepository().save(mapper.map(systemResultDto, SystemResult.class));
    }

    private void checkQuestionAndAnswerCode(SystemResultDto object) throws ElearningException {
        if ((abstractQuestionBankSercive.getOneByKey(object.getSystemResultId().getSystemResultQuestionCode()) == null)
                && (abstracAnswerBankService.getOneByKey(object.getSystemResultId().getSystemResultAnswerCode()) == null))
            throw new ElearningException(Errors.ANSWER_OR_QUESTION_NOT_EXITS.getId(),
                    Errors.ANSWER_OR_QUESTION_NOT_EXITS.getMessage());
    }

    private SystemResultId getSystemResultId(SystemResultDto object) {
        return new SystemResultId(object.getSystemResultId().getSystemResultQuestionCode(),
                object.getSystemResultId().getSystemResultAnswerCode());
    }
}
