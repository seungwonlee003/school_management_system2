package com.example.demo.exception;

public class UserNotEnrolledException extends RuntimeException{
    public UserNotEnrolledException(String userType) {
        super(userType + " is not enrolled in the subject");
    }
}
