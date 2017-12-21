package com.elearningbackend.controller;

import com.elearningbackend.dto.LessionDto;
import com.elearningbackend.service.ILessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LessionController {
    @Autowired
    private ILessionService lessionService;

}
