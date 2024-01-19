package com.example.demo.exception;

public class GradeNotFoundException extends RuntimeException{
    public GradeNotFoundException() {
        super("Grade was not found");
    }
}
