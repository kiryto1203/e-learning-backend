package com.elearningbackend.controller;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Result;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.dto.VerificationDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.service.AbstractUserService;
import com.elearningbackend.service.UserService;
import com.elearningbackend.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@CrossOrigin
public class SignUpController {
    @Autowired
    private AbstractUserService<UserDto,String,User> abstractUserService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<UserDto> signup(@Valid @RequestBody UserDto userDto){
        try {
            ServiceUtils.checkDataMissing(userDto,
                    "username", "password", "email");
            setUserInitData(userDto);
            String content =
                    String.format("Chào %s,<br/> Bạn vừa đăng ký tài khoản tại E Learning. Mã xác nhận của bạn là: %s"
                            ,userDto.getUsername(), userDto.getActivationDigest());
            abstractUserService.add(userDto);
            MailService.send(userDto.getEmail(),"Mã xác nhận E Learning",content);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), null);
        } catch (ElearningException e){
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        } catch (Exception e) {
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }

    private void setUserInitData(UserDto userDto) {
        userDto.setRole(Constants.AUTH_USER);
        userDto.setActivationDigest(RandomGenerator.random(Constants.DEFAULT_RANDOM_CHARACTER_LENGTH));
        userDto.setCreatedAt(new Date());
        userDto.setUpdatedAt(new Date());
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  Result<UserDto> verify(@Valid @RequestBody VerificationDto verificationDto){
        UserDto userDto = abstractUserService.getOneByEmail(verificationDto.getEmail());
        if(userDto==null)
            return new Result<>(Errors.USER_NOT_FOUND.getId(),
                    Errors.USER_NOT_FOUND.getMessage(), null);
        if(userDto.getActivated()==Constants.STATUS_LOCKED)
            return new Result<>(Errors.USER_LOCKED.getId(),
                    Errors.USER_LOCKED.getMessage(), null);
        if(userDto.getActivated()==Constants.STATUS_ACTIVATED)
            return new Result<>(Errors.USER_ACTIVATED.getId(),
                    Errors.USER_ACTIVATED.getMessage(), null);
        if(!userDto.getActivationDigest().equals(verificationDto.getActivationDigest().trim()))
            return new Result<>(Errors.ACTIVATION_CODE_NOT_MATCH.getId(),
                    Errors.ACTIVATION_CODE_NOT_MATCH.getMessage(), null);
        try {
            userDto.setActivatedAt(verificationDto.getActivatedAt());
            abstractUserService.active(userDto);
            return new Result<>(ResultCodes.OK.getCode(),
                    ResultCodes.OK.getMessage(), null);
        } catch (ElearningException e) {
            return new Result<>(e.getErrorCode(), e.getMessage(), null);
        }catch (Exception e) {
            return new Result<>(ResultCodes.FAIL_UNRECOGNIZED_ERROR.getCode(),
                    e.getMessage(), null);
        }
    }
}
