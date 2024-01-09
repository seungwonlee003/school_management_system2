package com.example.demo.controller;

import com.example.demo.model.Grade;
import com.example.demo.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeController {
    // see all grades of a user sorted by recent date
    // see all grades of a user by subject sorted by recent date
    // see cumulative grade from user by subject
    // see all grades by assignment (teacher)
    // see average grade by assignment
    private final GradeService gradeService;
    public ResponseEntity<List<Grade>> getAllGradesOfUser() {
        return new ResponseEntity<>(gradeService.getAllGradesForCurrentStudent(), HttpStatus.OK);
    }
    public ResponseEntity<List<Grade>> getAllGradesOfUserBySubject(Long subjectId){
        return new ResponseEntity<>(gradeService.getAllGradesForStudentInSubject(subjectId), HttpStatus.OK);

    }
}
