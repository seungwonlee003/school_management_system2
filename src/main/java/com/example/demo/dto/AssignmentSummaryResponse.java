package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentSummaryResponse {
    private String name;
    private LocalDateTime deadline;
}
