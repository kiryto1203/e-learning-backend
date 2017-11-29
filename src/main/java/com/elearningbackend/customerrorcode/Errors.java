package com.elearningbackend.customerrorcode;

public enum Errors {
    USER_ERROR("001","User errors"),
    USER_ERROR_FIELD_EXISTS("002","FIELD EXISTS"),
    USER_NOT_FOUND("003", "USER NOT FOUND"),
    USER_PASSWORD_NOT_MATCH("004", "PASSWORD NOT MATCH"),
    ERROR_FIELD_MISS("005","FIELD INPUT MISSING");

    private final String id;
    private final String message;

    Errors(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {return id;}

    public String getMessage() {return message;}
}
