package com.example.demo.exception;

public class AssignmentNotFoundException extends RuntimeException{
    public AssignmentNotFoundException(Long assignmentId) {
        super("Assignment not found with ID: " + assignmentId);
    }
}
