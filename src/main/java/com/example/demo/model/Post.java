package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    private LocalDateTime creationTime = LocalDateTime.now();
}
