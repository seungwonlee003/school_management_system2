package com.example.demo.controller;

import com.example.demo.model.Grade;
import com.example.demo.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/by-assignment/{assignmentId}")
    public ResponseEntity<Grade> getGradeOfCurrentUserByAssignment(@PathVariable Long assignmentId){
        return new ResponseEntity<>(gradeService.getGradeOfCurrentUserByAssignment(assignmentId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/of-user")
    public ResponseEntity<List<Grade>> getAllGradesOfCurrentUser() {
        return new ResponseEntity<>(gradeService.getAllGradesOfCurrentUser(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<Grade>> getAllGradesOfCurrentUserBySubject(@PathVariable Long subjectId){
        return new ResponseEntity<>(gradeService.getAllGradesOfCurrentUserBySubject(subjectId), HttpStatus.OK);
    }
}
