package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String name;
    private String description;
    private Long subjectId;
}
