package com.example.demo.controller;

import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @GetMapping("/of-user")
    public ResponseEntity<List<Subject>> getAllSubjectsOfCurrentUser() {
        return new ResponseEntity<>(subjectService.getAllSubjectsOfCurrentUser(), HttpStatus.OK);
    }
}
