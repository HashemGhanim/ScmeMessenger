package com.scme.messenger.constants;

import org.springframework.stereotype.Component;

@Component
public class ResponseConstants {

    private ResponseConstants() {

    }

    public static final String USER_NOT_FOUND = "User not Found, Try again please ...";
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201 = "created successfully";
    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String STATUS_417 = "417";
    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "An error occurred. Please try again or contact Dev team";

}
