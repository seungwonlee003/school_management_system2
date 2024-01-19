package com.example.demo.service;

import com.example.demo.exception.AssignmentNotFoundException;
import com.example.demo.exception.GradeNotFoundException;
import com.example.demo.exception.UserNotEnrolledException;
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
        Student student = authService.getStudent();
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        if (!authService.isUserEnrolledInSubject(assignment.getSubject().getId())) {
            throw new UserNotEnrolledException("Student");
        }
        return gradeRepository.findByAssignmentAndStudent(assignment, student)
                .orElseThrow(() -> new GradeNotFoundException());
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
        Student student = authService.getStudent();
        List<Assignment> assignments = assignmentService.getAllAssignmentsOfCurrentUserBySubject(subjectId);

        return assignments.stream()
                .flatMap(assignment -> gradeRepository.findByAssignmentAndStudent(assignment, student).stream())
                .toList();
    }

    // teacher
    public List<Grade> getAllGradesByAssignment(Long assignmentId) {
        Teacher teacher = authService.getTeacher();
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        if (!authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            throw new UserNotEnrolledException("Teacher");
        }
        return gradeRepository.findAllByAssignment(assignment);
    }

    // teacher
    public void editGrade(Long gradeId, double gradeVal) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(GradeNotFoundException::new);
        grade.setGrade(gradeVal);
        gradeRepository.save(grade);
    }
}
