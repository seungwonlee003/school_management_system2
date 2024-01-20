package com.example.demo.controller;

import com.example.demo.dto.EditGradeRequest;
import com.example.demo.model.Grade;
import com.example.demo.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/by-assignment/{assignmentId}")
    public ResponseEntity<Grade> getGradeOfCurrentUserByAssignment(@PathVariable Long assignmentId) {
        return new ResponseEntity<>(gradeService.getGradeOfCurrentUserByAssignment(assignmentId), HttpStatus.OK);
    }

    // fix default value of id
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/of-user")
    public ResponseEntity<Page<Grade>> getAllGradesOfCurrentUser(@RequestParam(defaultValue = "0") int offset,
                                                                 @RequestParam(defaultValue = "3") int pageSize,
                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(gradeService.getAllGradesOfCurrentUser(offset, pageSize, sortBy), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<Grade>> getAllGradesOfCurrentUserBySubject(@PathVariable Long subjectId) {
        return new ResponseEntity<>(gradeService.getAllGradesOfCurrentUserBySubject(subjectId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping("/by-assignment-all/{assignmentId}")
    public ResponseEntity<List<Grade>> getAllGradesByAssignment(@PathVariable Long assignmentId) {
        return new ResponseEntity<>(gradeService.getAllGradesByAssignment(assignmentId), HttpStatus.OK);
    }

    // somehow @RequestBody Double grade does not work so the editGradeDto was created instead for better json parsing
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PatchMapping("/edit/{gradeId}")
    public ResponseEntity<Void> editGrade(@PathVariable Long gradeId, @RequestBody EditGradeRequest editGradeRequest) {
        gradeService.editGrade(gradeId, editGradeRequest.getGrade());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
