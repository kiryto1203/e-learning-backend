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
        return String.format("%sQ%s", categoryCode, getCodePostFix());
    }

    public static String generateAnswerCode(){
        return String.format("%sA%s", getCodePostFix());
    }

    public static String getCodePostFix() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()).substring(0,6);
    }

    public static String generateFileUrl(String fileName) {
        return String.format("%s%s", String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()), fileName);
    }
}
