package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    // JsonIgnore used to avoid circular dependency. Will be removed with the creation of AssignmentDto.
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    private Subject subject;

    private LocalDateTime creationTime = LocalDateTime.now(); // Time of creation
    private LocalDateTime deadline; // Deadline
    private double gradeWeight; // Grade weight for the assignment
}
