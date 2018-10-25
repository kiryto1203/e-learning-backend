package com.elearningbackend.controller;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.LoginDto;
import com.elearningbackend.dto.Result;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.security.TokenAuthenticationService;
import com.elearningbackend.service.AbstractUserService;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.utility.ResultCodes;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    @Qualifier("userService")
    private AbstractUserService<UserDto, String, User> userService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result<String> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            String token = userService.login(loginDto);
            return new Result<>(ResultCodes.OK.getCode(), ResultCodes.OK.getMessage(), token);
        } catch (ElearningException e) {
            e.printStackTrace();
            return new Result<>(ResultCodes.LOGIN_FAIL.getCode(), e.getMessage(),null);
        }
    }
}
