package com.elearningbackend.controller;

import com.elearningbackend.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    protected Authentication authentication;
    protected UserDto userDto;

    /**
     * Method get Current User from Context
     * Can't call this method in Contructor because Context is null.
     * Each Action in Controller, Call this method to get Current User
     * @return UserDTO - CurrentUser
     */
    protected UserDto getCurrentUser(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDto) authentication.getPrincipal();
    }
}
