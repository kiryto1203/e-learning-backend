package com.elearningbackend.controller;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.Result;
import com.elearningbackend.dto.SystemResultDto;
import com.elearningbackend.entity.SystemResult;
import com.elearningbackend.entity.SystemResultId;
import com.elearningbackend.service.AbstractCustomService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ResultCodes;
import com.elearningbackend.utility.SortingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SystemResultController {
    @Autowired
    @Qualifier("systemResultService")
    private AbstractCustomService<SystemResultDto, SystemResultId, SystemResult> abstractService;

    @GetMapping("/system-result")
    public Pager<SystemResultDto> loadAll(
            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
            @RequestParam(defaultValue = SortingConstants.SORT_CATEGORY_SUBCATEGORY_DEFAULT_FIELD) String sortBy,
            @RequestParam(defaultValue = SortingConstants.ASC) String direction){
        return abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
    }

    @GetMapping("/system-result/{key)")
    public Result<SystemResultDto> getByKey(
            @PathVariable("key") SystemResultId key){
        try {
            SystemResultDto systemResultDto = abstractService.getOneByKey(key);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(),systemResultDto);
        }catch (ElearningException e){
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
    }

    @PostMapping("/system-result")
    public Result<SystemResultDto> add(
    ){
                //TODO
        return null;
    }

    //TODO
}
