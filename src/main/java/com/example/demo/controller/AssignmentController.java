package com.example.demo.controller;

import com.example.demo.model.Assignment;
import com.example.demo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Assign;
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

    @GetMapping("/")
    public ResponseEntity<List<Assignment>> getAllAssignmentsByUser(){
        return new ResponseEntity<>(assignmentService.getAllAssignmentsByUser(), HttpStatus.OK);
    }
    @GetMapping("/subject")
    public ResponseEntity<List<Assignment>> getAllAssignmentsBySubject(@RequestParam Long subjectId){
        return new ResponseEntity<>(assignmentService.getAllAssignmentsBySubject(subjectId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<Void> createAssignment(@RequestBody Assignment assignment){
        assignmentService.createAssignment(assignment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    // see all assignments sorted by recent date/deadline
    // see all assignments by subject sorted by recent date/deadline
    // create assignment
    // edit assignment
    // delete assignment

}
