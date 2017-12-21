package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.LessionDto;
import com.elearningbackend.dto.QuestionDto;
import com.elearningbackend.repository.IQuestionBankRepository;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class LessionService implements ILessionService {

    @Autowired
    private AbstractQuestionService<QuestionDto, String> questionService;

    @Autowired
    private IQuestionBankRepository iQuestionBankRepository;

    public LessionDto add(LessionDto lessionDto){
        try {
            ServiceUtils.checkDataMissing(lessionDto, "lessionUsername", "creationDate", "isFinish", "mappedLessionReports");
        } catch (ElearningException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<QuestionDto> generateLession(String subcategoryCode) {
        int currentPage = iQuestionBankRepository.countAllBySubcategorySubcategoryCode(subcategoryCode);
        int idx = (int)(Math.random() * currentPage);
        List<QuestionDto> randomResults = questionService.getQuestionsBySubcategoryCode(subcategoryCode, idx, currentPage).getResults();
        hideAnswerResult(randomResults);
        return null;
    }

    private void hideAnswerResult(List<QuestionDto> randomResults) {
        randomResults.forEach(e -> e.getAnswerDtos()
            .forEach(w -> w.getSystemResultDto().setSystemResultIsCorrect(Constants.ANSWER_HIDDEN_RESULT)));
    }
}
