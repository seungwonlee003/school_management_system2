package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    private Subject subject;
    private LocalDateTime creationTime = LocalDateTime.now(); // Time of creation
    private LocalDateTime deadline; // Deadline
    private double gradeWeight; // Grade weight for the assignment
}
