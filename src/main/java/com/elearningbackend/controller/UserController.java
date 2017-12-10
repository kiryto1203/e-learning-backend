package com.elearningbackend.controller;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.Result;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ResultCodes;
import com.elearningbackend.utility.ServiceUtils;
import com.elearningbackend.utility.SortingConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    @Qualifier("userService")
    private IAbstractService<UserDto, String> abstractService;

    @GetMapping("/users")
    public Pager<UserDto> loadAll(
            @RequestParam(value = "page", defaultValue = Constants.CURRENT_PAGE_DEFAULT_STRING_VALUE) int page,
            @RequestParam(value = "limit", defaultValue = Constants.NO_OF_ROWS_DEFAULT_STRING_VALUE) int noOfRowInPage,
            @RequestParam(defaultValue = SortingConstants.SORT_USER_DEFAULT_FIELD) String sortBy,
            @RequestParam(defaultValue = SortingConstants.ASC) String direction){
        return abstractService.loadAll(page, noOfRowInPage, sortBy, direction);
    }

    @GetMapping("/users/{key}")
    public Result<UserDto> getByKey(@PathVariable("key") String key){
        try {
            UserDto userDto = abstractService.getOneByKey(key);
            return new Result<>(ResultCodes.OK.getCode(),
                ResultCodes.OK.getMessage(), userDto);
        } catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }
    }

    @PostMapping("/users")
    public Result<UserDto> add(@Valid @RequestBody UserDto userDto){
        try {
            checkUserDataMissing(ServiceUtils.validateRequired(userDto,
                    "username", "password", "email", "phone", "role"));
            abstractService.add(userDto);
            return new Result<>(ResultCodes.OK.getCode(),
                ResultCodes.OK.getMessage(), userDto);
        } catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), userDto);
        } catch (Exception e) {
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                e.getMessage(), userDto);
        }
    }

    @PutMapping("/users/{key}")
    public Result<UserDto> edit(@PathVariable String key, @RequestBody UserDto userDto){
        userDto.setUsername(key);
        try {
            checkUserDataMissing(ServiceUtils.validateRequired(userDto,
                    "username", "password"));
            abstractService.edit(userDto);
            return new Result<>(ResultCodes.OK.getCode(),
                ResultCodes.OK.getMessage(), userDto);
        } catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), userDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                e.getMessage(), userDto);
        }
    }

    @DeleteMapping("/users/{key}")
    public Result<UserDto> delete(@PathVariable("key") String key){
        try {
            abstractService.delete(key);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), null);
        }catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }catch (Exception e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                e.getMessage(), null);
        }
    }

    private boolean checkUserDataMissing(Map<String, List<String>> errorsMap) throws ElearningException {
        ObjectMapper mapper = new ObjectMapper();
        if (!errorsMap.isEmpty()){
            throw new ElearningException(Errors.ERROR_FIELD_MISS.getId(), errorsMap.toString());
        }
        return true;
    }
}
