package com.example.demo.service;

import com.example.demo.model.Assignment;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final AuthService authService;
    private final GradeRepository gradeRepository;
    private final AssignmentService assignmentService;
    private final AssignmentRepository assignmentRepository;

    // student
    public Grade getGradeOfCurrentUserByAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException());
        if(!authService.isUserEnrolledInSubject(assignment.getSubject().getId())){
            throw new IllegalArgumentException();
        }
        Student currentStudent = authService.getStudent();

        return gradeRepository.findByAssignmentAndStudent(assignment, currentStudent)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    // student
    public List<Grade> getAllGradesOfCurrentUser() {
        Student student = authService.getStudent();
        return gradeRepository.findAllByStudent(student);
    }

    // student
    public List<Grade> getAllGradesOfCurrentUserBySubject(Long subjectId) {
        List<Assignment> assignments = assignmentService.getAllAssignmentsOfCurrentUserBySubject(subjectId);

        return assignments.stream()
                .flatMap(assignment -> gradeRepository.findByAssignment(assignment).stream())
                .toList();
    }

}
