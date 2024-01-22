package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentRequest{
    private String name;
    private String description;
    private Long subjectId;
    private LocalDateTime deadline;
    private double gradeWeight; 
}
