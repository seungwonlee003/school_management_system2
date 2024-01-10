package com.example.demo.controller;

import com.example.demo.model.Assignment;
import com.example.demo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/of-user")
    public ResponseEntity<List<Assignment>> getAllAssignmentsOfCurrentUser(){
        return new ResponseEntity<>(assignmentService.getAllAssignmentsOfCurrentUser(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<List<Assignment>> getAllAssignmentsOfCurrentUserBySubject(@PathVariable Long subjectId){
        return new ResponseEntity<>(assignmentService.getAllAssignmentsOfCurrentUserBySubject(subjectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<Void> createAssignment(@RequestBody Assignment assignment){
        assignmentService.createAssignment(assignment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
