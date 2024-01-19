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
import java.util.Set;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping("/of-user")
    public ResponseEntity<List<Subject>> getAllSubjectsOfCurrentUser() {
        return new ResponseEntity<>(subjectService.getAllSubjectsOfCurrentUser(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    @PostMapping("/enroll/{studentId}/{subjectId}")
    public ResponseEntity<Void> enrollStudentInSubject(@PathVariable Long studentId, @PathVariable Long subjectId) {
        subjectService.enrollStudentInSubject(studentId, subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // need sorting, bc its set
    @PreAuthorize("hasAnyRole('ROLE_TEACHER')")
    @GetMapping("/student/{subjectId}")
    public ResponseEntity<Set<Student>> getAllStudentsBySubject(@PathVariable Long subjectId){
        return new ResponseEntity<>(subjectService.getAllStudentsBySubject(subjectId), HttpStatus.OK);
    }

    // find all students by subject accessible to teachers only
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createSubject(@RequestBody Subject subject) {
        subjectService.createSubject(subject);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
