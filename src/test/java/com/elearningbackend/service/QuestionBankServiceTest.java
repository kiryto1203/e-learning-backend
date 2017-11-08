package com.elearningbackend.service;

import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.QuestionBankDto;
import com.elearningbackend.entity.QuestionBank;
import com.elearningbackend.repository.IQuestionBankRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JMockit.class)
public class QuestionBankServiceTest {
    @Tested
    private QuestionBankService questionBankService;

    @Injectable
    IQuestionBankRepository iQuestionBankRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Test
    public void returnQuestionBankDtoPagerOnPaginateWhenParamsAreValid(@Mocked Page<QuestionBank> pager,
                     @Mocked QuestionBankDto question1, @Mocked QuestionBankDto question2) throws Exception {
        Pager<QuestionBankDto> result = new Pager<>();
        List<QuestionBankDto> questionBankDtos = new ArrayList<>();
        questionBankDtos.add(question1);
        questionBankDtos.add(question2);
        List<QuestionBank> questionBanks = questionBankDtos.stream().map(a -> mapper.map(a, QuestionBank.class)).collect(Collectors.toList());
        result.setCurrentPage(3);
        result.setTotalRow(30);
        result.setNoOfRowInPage(10);
        result.setTotalPage(3);
        result.setResults(questionBankDtos);
        new Expectations(){{
            pager.getTotalElements(); result = 30;
            pager.getContent(); result = questionBanks;
        }};
        assertThat(questionBankService.paginate(3, pager, 10).getCurrentPage(), is(3));
        assertThat(questionBankService.paginate(3, pager, 10).getNoOfRowInPage(), is(10));
        assertThat(questionBankService.paginate(3, pager, 10).getTotalPage(), is(3L));
        assertThat(questionBankService.paginate(3, pager, 10).getTotalRow(), is(30L));
    }
}
