package com.elearningbackend.service;

import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.LessonDto;
import com.elearningbackend.dto.Pager;
import com.elearningbackend.dto.UserDto;

public interface ILessonService {
    LessonDto startLesson(UserDto userDto, String subcategoryCode) throws ElearningException;
    Pager<LessonDto> loadAll(CurrentUser currentUser, int isFinish, int currentPage, int noOfRowInPage, String sortBy, String direction) throws ElearningException;
    LessonDto getOneByKey(String lessonCode) throws ElearningException;
    LessonDto edit(UserDto userDto, String lessonCode, LessonDto lessonDto) throws ElearningException;
}
