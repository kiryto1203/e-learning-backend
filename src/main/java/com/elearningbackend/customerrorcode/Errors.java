package com.elearningbackend.customerrorcode;

public enum Errors {
    USER_ERROR("001","User errors"),
    USER_EXISTS("002","USER EXISTS"),
    USER_NOT_FOUND("003", "USER NOT FOUND"),
    USER_PASSWORD_NOT_MATCH("004", "PASSWORD NOT MATCH"),
    ERROR_FIELD_MISS("005","FIELD INPUT MISSING"), 
    EMAIL_EXISTS("006", "EMAIL EXISTS"),
    EMAIL_SAME_WITH_OTHER_USERS("007", "EMAIL SAME WITH OTHER USERS");

    private final String id;
    private final String message;

    Errors(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {return id;}

    public String getMessage() {return message;}
}
