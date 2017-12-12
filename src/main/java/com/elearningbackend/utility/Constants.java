package com.elearningbackend.utility;

public interface Constants {
    int ZERO = 0;
    int NO_OF_ROWS_DEFAULT_VALUE = 10;
    String NO_OF_ROWS_DEFAULT_STRING_VALUE = "10";
    int CURRENT_PAGE_DEFAULT_VALUE = 0;
    String CURRENT_PAGE_DEFAULT_STRING_VALUE = "0";

    /**
     * Secret Authenticate and authorize
     */
    String SECRET = "pk78syiEByfpCRHp5GdA7K0Z4+4dvc1X73fibeF0yCdh3z4kvCOOobqfF/ewWQIbfQP+0wuBvH4S9sw9FD2OAA==";
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
    long EXPIRATION_TIME = 3_600_000;

    /**
     * List authorize
     */
    String AUTH_ADMINISTRATOR = "hasAuthority('0')";
    String AUTH_MANAGER = "hasAuthority('1')";
    String AUTH_TEACHER = "hasAuthority('2')";
    String AUTH_CONTRIBUTER = "hasAuthority('3')";
    String AUTH_USER = "hasAuthority('4')";

    /**
     * list status user
     */
    int STATUS_ACTIVATED = 1;
    int STATUS_NOT_ACTIVATED = 0;
    int STATUS_LOCKED = 2;
}
