package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private String name;
    private String description;
    private String subjectName;
    private String userName;
    private LocalDateTime creationTime;
}
