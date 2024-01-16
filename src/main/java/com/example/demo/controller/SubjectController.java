package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createSubject(@RequestBody Subject subject) {
        subjectService.createSubject(subject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
