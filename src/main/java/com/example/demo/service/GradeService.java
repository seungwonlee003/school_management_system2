package com.example.demo.service;

import com.example.demo.model.Assignment;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        if (!authService.isUserEnrolledInSubject(assignment.getSubject().getId())) {
            throw new IllegalArgumentException();
        }
        Student currentStudent = authService.getStudent();

        return gradeRepository.findByAssignmentAndStudent(assignment, currentStudent)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    // student
    public Page<Grade> getAllGradesOfCurrentUser(int offset, int pageSize, String sortBy) {
        Student student = authService.getStudent();
        Sort sort = Sort.by(sortBy);
        PageRequest pageable = PageRequest.of(offset, pageSize, sort);
        return gradeRepository.findAllByStudent(student, pageable);
    }

    // student
    public List<Grade> getAllGradesOfCurrentUserBySubject(Long subjectId) {
        List<Assignment> assignments = assignmentService.getAllAssignmentsOfCurrentUserBySubject(subjectId);

        return assignments.stream()
                .flatMap(assignment -> gradeRepository.findByAssignment(assignment).stream())
                .toList();
    }

    // teacher
    public List<Grade> getAllGradesByAssignment(Long assignmentId) {
        Teacher teacher = authService.getTeacher();
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException());
        if (!authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            throw new RuntimeException();
        }
        return gradeRepository.findAllByAssignment(assignment);
    }

    public void editGrade(Long gradeId, double gradeVal) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> new IllegalArgumentException());
        grade.setGrade(gradeVal);
        gradeRepository.save(grade);
    }
}
