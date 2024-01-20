package com.example.demo.controller;

import com.example.demo.model.Assignment;
import com.example.demo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping("/of-user")
    public ResponseEntity<Page<Assignment>> getAllAssignmentsOfCurrentUser(@RequestParam(defaultValue = "0") int offset,
                                                                       @RequestParam(defaultValue = "3") int pageSize,
                                                                       @RequestParam(defaultValue = "deadline") String sortBy){
        return new ResponseEntity<>(assignmentService.getAllAssignmentsOfCurrentUser(offset, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<Page<Assignment>> getAllAssignmentsOfCurrentUserBySubject(@RequestParam(defaultValue = "0") int offset,
                                                                                    @RequestParam(defaultValue = "5") int pageSize,
                                                                                    @RequestParam(defaultValue = "deadline") String sortBy,
                                                                                    @PathVariable Long subjectId){
        return new ResponseEntity<>(assignmentService.getAllAssignmentsOfCurrentUserBySubject(offset, pageSize, sortBy, subjectId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<Void> createAssignment(@RequestBody Assignment assignment){
        assignmentService.createAssignment(assignment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}