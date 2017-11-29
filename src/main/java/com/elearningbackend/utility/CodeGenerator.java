/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elearningbackend.utility;

import java.sql.Timestamp;

/**
 *
 * @author c1508l3694
 */
public abstract class CodeGenerator {
    public static String generateQuestionCode(String categoryCode){
        return String.format("%sQ%s", categoryCode, new Timestamp(System.currentTimeMillis()).getTime());
    }

    public static String generateAnswerCode(String answerCode){
        return String.format("%sA%s",answerCode, new Timestamp(System.currentTimeMillis()).getTime());
    }
}
