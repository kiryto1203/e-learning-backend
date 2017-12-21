package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.customexception.ElearningMapException;
import com.elearningbackend.dto.*;
import com.elearningbackend.entity.*;
import com.elearningbackend.utility.CodeGenerator;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.Paginator;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = ElearningException.class)
public class QuestionService extends AbstractQuestionService<QuestionDto,String> {

    @Autowired
    private AbstractCustomService<AnswerBankDto,String,AnswerBank> answerBankService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private AbstractSystemResultService<SystemResultDto,SystemResultId,SystemResult> systemResultService;

    @Override
    public List<QuestionDto> add(List<QuestionDto> object) throws ElearningException {
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (QuestionDto questionDto : object ) {
            try{
                questionDtos.add(addOneQuestionDto(object, questionDto));
            }catch(ElearningMapException e){
                throw new ElearningMapException(e.getExceptionMap(),e.getErrorCode(),e.getMessage());
            }catch (ElearningException e){
                throw new ElearningException(e.getErrorCode(),
                        e.getMessage());
            }
        }
        return questionDtos;
    }

    @Override
    public List<AnswerDto> getAnswers(String questionCode,int fetchType, int length) {
        List<SystemResultDto> systemResultDtos = systemResultService.getSystemResultByQuestionCode(questionCode);
        return addAnswerDtoAndSystemResult(systemResultDtos,fetchType,length);
    }

    @Override
    public Pager<QuestionDto> getQuestionsBySubcategoryCode(String subcategoryCode, int currentPage, int noOfRowInPage) {
        return convertToPagerQuestionDto(questionBankService.getBySubcategoryCode(subcategoryCode, currentPage, noOfRowInPage));
    }

    private Pager<QuestionDto> convertToPagerQuestionDto(Pager<QuestionBankDto> questionBankDtoPager){
        Pager<QuestionDto> questionDtoPager = new Pager<>();
        questionDtoPager.setTotalPage(questionBankDtoPager.getTotalPage());
        questionDtoPager.setNoOfRowInPage(questionBankDtoPager.getNoOfRowInPage());
        questionDtoPager.setCurrentPage(questionBankDtoPager.getCurrentPage());
        questionDtoPager.setTotalRow(questionBankDtoPager.getTotalRow());
        List<QuestionDto> questionDtos = questionBankDtoPager.getResults().stream().map(e -> {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQuestionBankDto(e);
            questionDto.setAnswerDtos(getAnswers(e.getQuestionCode(), Constants.FETCH_ALL_ANSWERS, Constants.FETCH_ALL_ANSWERS));
            return questionDto;
        }).collect(Collectors.toList());
        questionDtoPager.setResults(questionDtos);
        return questionDtoPager;
    }

    @Override
    public QuestionDto getOneByKey(String key) throws ElearningException {
        QuestionDto questionDto = new QuestionDto();
        QuestionBankDto questionBankDto = questionBankService.getOneByKey(key);
        questionBankDto.getSubcategory().setQuestionBanks(null);
        questionDto.setQuestionBankDto(questionBankDto);
        List<SystemResultDto> systemResultDtos = systemResultService.getSystemResultByQuestionCode(key);
        questionDto.setAnswerDtos(addAnswerDtoAndSystemResult(systemResultDtos,Constants.FETCH_ALL_ANSWERS,Constants.FETCH_ALL_ANSWERS));
        return questionDto;
    }

    @Override
    public QuestionDto edit(QuestionDto object) throws ElearningException {
        ServiceUtils.checkDataMissing(object.getQuestionBankDto(),"questionCode","questionContent","questionType",
                "subcategory","point");
        QuestionBankDto questionBankDto = questionBankService.edit(object.getQuestionBankDto());
        for (AnswerDto answerDto: object.getAnswerDtos()) {
            ServiceUtils.checkDataMissing(answerDto.getAnswerBankDto(),"answerContent");
            ServiceUtils.checkDataMissing(answerDto.getSystemResultDto(),"systemResultPosition","systemResultIsCorrect");
            if(answerDto.getAnswerBankDto().getAnswerCode()!=null){
                answerBankService.edit(answerDto.getAnswerBankDto());
                systemResultService.delete(new SystemResultId(questionBankDto.getQuestionCode(),
                        answerDto.getAnswerBankDto().getAnswerCode()));
            }
        }
        editSystemResullt(object);
        return object;
    }

    @Override
    public QuestionDto delete(String key) throws ElearningException {
        QuestionDto questionDto = getOneByKey(key);
        List<SystemResultDto> systemResultByQuestionCode = systemResultService.getSystemResultByQuestionCode(key);
        for (SystemResultDto systemResultDto: systemResultByQuestionCode){
            systemResultService.delete(new SystemResultId(systemResultDto.getQuestionBank().getQuestionCode(),
                    systemResultDto.getAnswerBank().getAnswerCode()));
        }
        QuestionBankDto questionBankDto = questionBankService.delete(key);
        return questionDto;
    }

    private QuestionDto addOneQuestionDto(List<QuestionDto> questionDtos, QuestionDto questionDto) throws ElearningException {
        /**
         * get questionBankDto from QuestionDto
         */
        QuestionBankDto questionBankDto = questionDto.getQuestionBankDto();
        validateQuestionType(questionDto);
        ServiceUtils.checkDataMissing(questionBankDto, "questionType", "questionContent", "point", "subcategory");
        checkQuestionParent(questionBankDto);
        /**
         * add question to db
         * get old question code to check it has child question
         */
        String oldQuestionCode = questionDto.getQuestionBankDto().getQuestionCode();
        String questionCode = CodeGenerator.generateQuestionCode(questionBankDto.getSubcategory().getSubcategoryCode());
        questionBankDto.setQuestionCode(questionCode);
        QuestionBankDto questionBankDtoDB = questionBankService.addOrGetExists(questionBankDto);
        /**
         * fix loop reference self if questionBankDto get from db
         * => questionBankDto.subcategory get from db
         * => questionBankDto.subcategory containt list questionBankDto => error loop reference seft
         */
        questionBankDtoDB.setSubcategory(questionDto.getQuestionBankDto().getSubcategory());
        questionDto.setQuestionBankDto(questionBankDtoDB);
        formatChildQuestion(questionDtos,questionDto,questionBankDto,oldQuestionCode);
        editSystemResullt(questionDto);
        return questionDto;
    }

    private void checkQuestionParent(QuestionBankDto questionBankDto) throws ElearningException {
        /**
         * if this is children question (it has questionparentcode)
         * and this question's parent not exists,
         * is can't add to db and throw Exception
         */
        if (questionBankDto.getQuestionParentCode() != null){
            try{
                questionBankService.getOneByKey(questionBankDto.getQuestionParentCode());
            }catch (ElearningException e){
                throw new ElearningException(Errors.QUESTION_BANK_PRARENT_NOT_EXISTS.getId(),
                        Errors.QUESTION_BANK_PRARENT_NOT_EXISTS.getMessage());
            }
        }
    }

    private void validateQuestionType(QuestionDto questionDto) throws ElearningException {
        if(questionDto.getQuestionBankDto().getQuestionType() == Constants.Q_TYPE_CHOOSE_ONE
                || questionDto.getQuestionBankDto().getQuestionType() == Constants.Q_TYPE_CHOOSE_MULTIPLE )
        {
            if(questionDto.getAnswerDtos().size()<Constants.LENGTH_ANSWER_DEFAULT)
                throw new ElearningException(Errors.ANSWER_TOO_LESS.getId(),
                        Errors.ANSWER_TOO_LESS.getMessage());
            if(!hasCorrectAnswer(questionDto.getAnswerDtos()))
                throw new ElearningException(Errors.NOT_ANSWER_CORRECT.getId(),
                        Errors.NOT_ANSWER_CORRECT.getMessage());
        }
    }

    private void formatChildQuestion(List<QuestionDto> questionDtos, QuestionDto questionDto, QuestionBankDto questionBankDto,String oldQuestionCode){
        /**
         * Check this question has child
         * if result is true
         * => change children's question code by new parent's question code
         * Purpose: don't set parent's question code for each children question
         */
        if(hasChildQuestion(questionDtos,oldQuestionCode)){
            changeQuestionParentCode(questionDtos,oldQuestionCode
                    , questionDto.getQuestionBankDto().getQuestionCode());
        }
    }

    private boolean hasCorrectAnswer(List<AnswerDto> answerDtos){
        return answerDtos.stream().anyMatch(e -> e.getSystemResultDto().getSystemResultIsCorrect()==Constants.FETCH_ANSWER_CORRECT);
    }

    private boolean hasChildQuestion(List<QuestionDto> questionDtos, String questionCode){
        return questionDtos.stream().anyMatch(e -> e.getQuestionBankDto().getQuestionParentCode()!=null && e.getQuestionBankDto().getQuestionParentCode().equals(questionCode));
    }

    private void changeQuestionParentCode(List<QuestionDto> questionDtos, String oldQuestionCode, String newQuestionCode){
        questionDtos.stream().forEach(e -> {
            if(e.getQuestionBankDto().getQuestionParentCode() != null &&
            e.getQuestionBankDto().getQuestionParentCode().equals(oldQuestionCode)){
                e.getQuestionBankDto().setQuestionParentCode(newQuestionCode);
            }
        });
    }

    private List<AnswerDto> addAnswerDtoAndSystemResult(List<SystemResultDto> systemResultDtos,int fetchType,int length){
        List<AnswerDto> answerDtos= new ArrayList<>();
        AnswerDto answerDto = null;
        for (int i = 0, size = systemResultDtos.size(); i < size; i++) {
            if(length > 0 && i >length)
                break;
            if(fetchType!= Constants.FETCH_ALL_ANSWERS && isSameFetchType(fetchType,systemResultDtos.get(i)))
                continue;
            answerDto = new AnswerDto();
            answerDto.setAnswerBankDto(mapper.map(systemResultDtos.get(i).getAnswerBank(),AnswerBankDto.class));
            ignoreSystemResultDto(systemResultDtos.get(i));
            answerDto.setSystemResultDto(systemResultDtos.get(i));
            answerDtos.add(answerDto);
        }
        return answerDtos;
    }

    private boolean isSameFetchType(int fetchType,SystemResultDto systemResultDto){
        return fetchType != systemResultDto.getSystemResultIsCorrect();
    }

    private void ignoreSystemResultDto(SystemResultDto systemResultDto){
        systemResultDto.setQuestionBank(null);
        systemResultDto.setAnswerBank(null);
    }

    private void editSystemResullt(QuestionDto questionDto) throws ElearningMapException {
        Map<Integer,ElearningException> errorMap = new   HashMap<>();
        if(questionDto.getQuestionBankDto().getQuestionType() != Constants.Q_TYPE_PARAGRAPH){
            for (int i = 0, length = questionDto.getAnswerDtos().size(); i < length; i++) {
                try{
                    AnswerBankDto answerBankDto = questionDto.getAnswerDtos().get(i).getAnswerBankDto();
                    ServiceUtils.checkDataMissing(answerBankDto,"answerContent");
                    answerBankDto.setAnswerCode(CodeGenerator.generateAnswerCode());
                    questionDto.getAnswerDtos().get(i)
                            .setAnswerBankDto(answerBankService.addOrGetExists(answerBankDto));
                    try {
                        systemResultService.getOneByKey(new SystemResultId(questionDto.getQuestionBankDto().getQuestionCode(),
                                questionDto.getAnswerDtos().get(i).getAnswerBankDto().getAnswerCode()));
                        systemResultService.delete(new SystemResultId(questionDto.getQuestionBankDto().getQuestionCode(),
                                questionDto.getAnswerDtos().get(i).getAnswerBankDto().getAnswerCode()));
                    }catch(ElearningException e){}
                    SystemResultDto systemResultDto = questionDto.getAnswerDtos().get(i).getSystemResultDto();
                    SystemResultId systemResultId = new SystemResultId(questionDto.getQuestionBankDto().getQuestionCode(),
                            questionDto.getAnswerDtos().get(i).getAnswerBankDto().getAnswerCode());
                    ServiceUtils.checkDataMissing(systemResultDto,"systemResultIsCorrect","systemResultPosition");
                    systemResultDto.setSystemResultId(systemResultId);
                    SystemResultDto systemResultDto1 = systemResultService.addOrGetExists(systemResultDto);
                    ignoreSystemResultDto(systemResultDto1);
                    questionDto.getAnswerDtos().get(i).setSystemResultDto(systemResultDto1);
                }catch(ElearningException e){
                    errorMap.put(i,e);
                }
            }
        }
        if(errorMap.size()>0)
            throw new ElearningMapException(errorMap,Errors.MULTIPLE_EXCEPTION_OCCURS.getId()
                    ,Errors.MULTIPLE_EXCEPTION_OCCURS.getMessage());
    }
}
