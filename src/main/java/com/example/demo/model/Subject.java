package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //@ManyToMany(mappedBy = "subjects")
    // does making below eager do anything?
    @ManyToMany(fetch= FetchType.EAGER, mappedBy = "subjects")
    private Set<Student> students;
    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers;
}
