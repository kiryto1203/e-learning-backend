package com.elearningbackend.service;

import com.elearningbackend.dto.AnswerBankDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.entity.AnswerBank;
import com.elearningbackend.repository.IAnswerBankRepository;
import com.elearningbackend.utility.Paginator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class AnswerBankService implements IAnswerBankService{

    @Autowired
    private IAnswerBankRepository iAnswerBankRepository;

    private ModelMapper modelMapper = new ModelMapper();
    private final Paginator<AnswerBank, AnswerBankDto> paginator = new Paginator<>(AnswerBankDto.class);

    @Override
    public Pager<AnswerBankDto> loadAllAnswers(int currentPage, int noOfRowInPage) {
        Page<AnswerBank> pager = iAnswerBankRepository.findAll(new PageRequest(currentPage, noOfRowInPage));
        return paginator.paginate(currentPage, pager, noOfRowInPage, modelMapper);
    }

    @Override
    public AnswerBankDto getAnswerByCode(String answerCode) {
        AnswerBank answerBank = iAnswerBankRepository.findOne(answerCode);
        return answerBank == null ? null : modelMapper.map(answerBank, AnswerBankDto.class);
    }

    @Override
    public Pager<AnswerBankDto> getAnswerByCreator(String creatorUsername, int currentPage, int noOfRowInPage) {
        //Pager<AnswerBankDto> pager = iAnswerBankRepository.
        return null;
    }

    @Override
    public AnswerBankDto addNewAnswer(AnswerBankDto answer) throws Exception {
        return null;
    }

    @Override
    public AnswerBankDto editAnswer(AnswerBankDto answer) throws Exception {
        return null;
    }

    @Override
    public AnswerBankDto deleteAnswer(String answerCode) throws Exception {
        return null;
    }
}
