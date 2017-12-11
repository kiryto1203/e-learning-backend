package com.elearningbackend.customerrorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Errors {
    USER_ERROR("001","User errors"),
    USER_EXISTS("002","USER EXISTS"),
    USER_NOT_FOUND("003", "USER NOT FOUND"),
    USER_PASSWORD_NOT_MATCH("004", "PASSWORD NOT MATCH"),
    ERROR_FIELD_MISS("005","FIELD INPUT MISSING"), 
    EMAIL_EXISTS("006", "EMAIL EXISTS"),
    EMAIL_SAME_WITH_OTHER_USERS("007", "EMAIL SAME WITH OTHER USERS"),
    CATEGORY_NOT_FOUND("008", "CATEGORY NOT FOUND"),
    SUBCATEGORY_NOT_FOUND("009", "SUBCATEGORY NOT FOUND"),
    ANSWER_EXIST("010", "ANSWER EXIST"),
    ANSWER_NOT_EXITS("011", "ANSWER NOT EXIST"),
    USERNAME_AND_PASSWORD_IS_NOT_EMPTY("050","USERNAME AND PASSWORD IS NOT EMPTY"),
    NOT_TOKEN("051","NOT TOKEN"),
    TOKEN_NOT_MATCH("052","TOKEN NOT MATCH"),
    ACCESS_DENIED("053","ACCESS DENIED"),
    USER_NOT_ACTIVATED("054","USER NOT ACTIVATED"),
    USER_ACTIVATED("056","USER ACTIVATED"),
    ERROR_SENT_MAIL("080","ERROR SENT MAIL"),
    ACTIVATION_CODE_NOT_MATCH("081","ACTIVATION CODE NOT MATCH"),
    USER_LOCKED("055","USER LOCKED"),
    CANNOT_STORE_FILE("099", "CANNOT COPY FILE"),
    IMAGE_FILE_NOT_SUPPORTED("90", "IMAGE FILE NOT SUPPORTED"),
    IMAGE_FILE_EMPTY("95", "IMAGE FILE CANNOT BE EMPTY"),
    IMAGE_FILE_TOO_LARGE("98", "IMAGE FILE EXCEED 3MB"),
    CANNOT_CREATE_USER_FOLDER("93", "CANNOT CREATE USER FOLDER"),
    ERROR_UPDATE_AVATAR("91", "ERROR UPDATE AVATAR"),
    CANNOT_GET_FILE("92", "CANNOT GET FILE"),
    CANNOT_DELETE_FILE("111", "CANNOT DELETE FILE"),
    IMAGE_FILE_CORRUPTED("70", "IMAGE FILE CORRUPTED");

    private final String id;
    private final String message;

    public String getAdditionalMessage(String additionalMessage) {return message+": "+additionalMessage;}
}
