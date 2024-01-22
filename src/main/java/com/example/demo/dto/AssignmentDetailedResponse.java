package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentDetailedResponse {
    private String name;
    private String description;
    private LocalDateTime creationTime;
    private LocalDateTime deadline;
    private double gradeWeight;
}
