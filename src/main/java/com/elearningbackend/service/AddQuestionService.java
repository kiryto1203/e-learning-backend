package com.elearningbackend.service;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.*;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.entity.SystemResultId;
import com.elearningbackend.utility.CodeGenerator;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AddQuestionService extends AbstractCommonService<AddQuestionDto,SystemResultId> {

    @Autowired
    private AbstractCustomService<AnswerBankDto,String,AnswerBank> answerBankService;
    @Autowired
    private AbstractCustomService<QuestionBankDto,String,QuestionBank> questionBankService;
    @Autowired
    private AbstractCustomService<SystemResultDto,SystemResultId,SystemResult> systemResultService;

    public AddQuestionService(){
        Map<String, IAbstractService> repositoryMap = new HashMap<>();
        repositoryMap.put(QuestionBank.class.getName(),questionBankService);
        repositoryMap.put(AnswerBank.class.getName(),answerBankService);
        repositoryMap.put(SystemResult.class.getName(),systemResultService);
    }

    public AddQuestionService(HashMap<String,IAbstractService> repositories) {
        super(repositories);
    }

    @Override
    public List<AddQuestionDto> add(List<AddQuestionDto> object,String username) throws ElearningException {
        List<AddQuestionDto> addQuestionDtos = new ArrayList();
        for (AddQuestionDto addQuestionDto: object ) {
            try{
                addQuestionDtos.add(addOneQuestionDto(object,addQuestionDto,username));
            }catch (ElearningException e){
                throw new ElearningException(Errors.ADD_QUESTIONS_ERROR.getId(),
                        Errors.ADD_QUESTIONS_ERROR.getId());
            }
        }
        return addQuestionDtos;
    }

    /**
     * Add one AddQuestionDto
     * if error occurs , return null and don't do anything
     * @param addQuestionDto
     * @return
     */

    private AddQuestionDto addOneQuestionDto(List<AddQuestionDto> addQuestionDtos, AddQuestionDto addQuestionDto,String username) throws ElearningException {
        /**
         * get questionBankDto from AddQuestionDto
         */
        QuestionBankDto questionBankDto = addQuestionDto.getQuestionBankDto();
        /**
         * check question type
         * if question type is not same question type paragraph (question parent)
         * check correct answer in list answer of this question
         * if it doesn't any correct anwswer , it can't add to db, we throw exception
         */
        if (questionBankDto.getQuestionType() != Constants.Q_TYPE_PARAGRAPH && !hasCorrectAnswer(addQuestionDto.getAnswers()))
            throw new ElearningException(Errors.NOT_ANSWER_CORRECT.getId(), Errors.NOT_ANSWER_CORRECT.getMessage());
        ServiceUtils.checkDataMissing(questionBankDto, "questionType", "questionContent", "point", "subcategory");

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
        String questionCode = CodeGenerator.generateQuestionCode(questionBankDto.getSubcategory().getSubcategoryCode());
        /**
         * Check this question has children
         * if result is true
         * => change children's question code by new parent's question code
         * Purpose: don't set parent's question code for each children question
         */
        if(hasChildrenQuestion(addQuestionDtos,questionBankDto.getQuestionCode())){
            changeQuestionParentCode(addQuestionDtos,questionBankDto.getQuestionCode(),questionCode);
        }
        /**
         * add question to db
         */
        questionBankDto.setCreatorUsername(username);
        questionBankDto.setQuestionCode(questionCode);
        addQuestionDto.setQuestionBankDto(questionBankService.add(questionBankDto));

        if(questionBankDto.getQuestionType() != Constants.Q_TYPE_PARAGRAPH){
            for (int i = 0, length = addQuestionDto.getAnswers().size(); i < length; i++) {
                /**
                 * add answerbank
                 */
                AnswerBankDto answerBankDto = addQuestionDto.getAnswers().get(i).getAnswerBankDto();
                ServiceUtils.checkDataMissing(answerBankDto,"answerContent");
                answerBankDto.setCreatorUsername(username);
                /**
                 * get answer by answer code
                 * if is is null or not exist answer
                 * answer will add to db
                 */
                try{
                    if(answerBankDto.getAnswerCode()== null)
                        throw new ElearningException(Errors.ANSWER_NOT_EXITS.getId(),Errors.ANSWER_NOT_EXITS.getId());
                    answerBankService.getOneByKey(answerBankDto.getAnswerCode());
                }catch(ElearningException e){
                    addAnswerDTO(addQuestionDto.getAnswers().get(i),answerBankDto);
                }
                /**
                 * add system result
                 */
                SystemResultDto systemResultDto = addQuestionDto.getAnswers().get(i).getSystemResultDto();
                SystemResultId systemResultId = new SystemResultId(questionCode,answerBankDto.getAnswerCode());
                ServiceUtils.checkDataMissing(systemResultDto,"system_result_is_correct","system_result_position");
                systemResultDto.setSystemResultId(systemResultId);
                addQuestionDto.getAnswers().get(i).setSystemResultDto(systemResultService.add(systemResultDto));
            }
        }
        return addQuestionDto;
    }

    private boolean hasCorrectAnswer(List<AddAnswerDto> addAnswerDtos){
       return addAnswerDtos.stream().anyMatch(e -> e.getSystemResultDto().getSystemResultIsCorrect()==Constants.ANSWER_CORRECT);
    }

    private boolean hasChildrenQuestion(List<AddQuestionDto> addQuestionDtos, String questiongCode){
        return addQuestionDtos.stream().anyMatch(e -> e.getQuestionBankDto().getQuestionParentCode()!=null && e.getQuestionBankDto().getQuestionParentCode().equals(questiongCode));
    }

    private void changeQuestionParentCode(List<AddQuestionDto> addQuestionDtos,String oldQuestionCode,String newQuestionCode){
        addQuestionDtos.stream().map(e -> {
            if(e.getQuestionBankDto().getQuestionCode().equals(oldQuestionCode)){
                e.getQuestionBankDto().setQuestionCode(newQuestionCode);
            }
            return e;
        });
    }

    private void addAnswerDTO(AddAnswerDto addAnswerDto,AnswerBankDto answerBankDto) throws ElearningException {
        answerBankDto.setAnswerCode(CodeGenerator.generateAnswerCode());
        addAnswerDto.setAnswerBankDto(answerBankService.add(answerBankDto));
    }

    @Override
    public AddQuestionDto getOneByKey(SystemResultId key) throws ElearningException {
        return null;
    }

    @Override
    public AddQuestionDto edit(AddQuestionDto object) throws ElearningException {
        return null;
    }

    @Override
    public AddQuestionDto delete(SystemResultId key) throws ElearningException {
        return null;
    }
}
