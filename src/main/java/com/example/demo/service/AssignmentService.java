package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.GradeRepository;
import jakarta.persistence.SecondaryTable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final SubjectService subjectService;

    private final AuthService authService;

    private final GradeRepository gradeRepository;

    public Page<Assignment> getAllAssignmentsOfCurrentUser(int offset, int pageSize, String sortBy) {
        List<Subject> subjects = subjectService.getAllSubjectsOfCurrentUser();

        List<Long> subjectIds = subjects.stream()
                .map(Subject::getId)
                .toList();

        Sort sort = Sort.by(sortBy);

        PageRequest pageable = PageRequest.of(offset, pageSize, sort);

        return assignmentRepository.findAllBySubject_IdIn(subjectIds, pageable);
    }

    public Page<Assignment> getAllAssignmentsOfCurrentUserBySubject(int offset, int pageSize, String sortBy, Long subjectId) {
        if (!authService.isStudentEnrolledInSubject(subjectId)) {
            throw new RuntimeException();
        }
        Sort sort = Sort.by(sortBy);

        PageRequest pageable = PageRequest.of(offset, pageSize, sort);

        return assignmentRepository.findAllBySubject_Id(subjectId, pageable);
    }

    public void createAssignment(Assignment assignment) {
        Teacher teacher = authService.getTeacher();
        if (authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            assignmentRepository.save(assignment);
        }
        Set<Student> allStudents = assignment.getSubject().getStudents();
        for (Student student : allStudents) {
            Grade grade = new Grade();
            grade.setAssignment(assignment);
            grade.setStudent(student);
            gradeRepository.save(grade);
        }
    }
    public void deleteAssignment(Long assignmentId) {
        Teacher teacher = authService.getTeacher();
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException());
        if (authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            assignmentRepository.deleteById(assignmentId);
        }
    }

    public List<Assignment> getAllAssignmentsOfCurrentUser() {
        List<Subject> subjects = subjectService.getAllSubjectsOfCurrentUser();

        List<Long> subjectIds = subjects.stream()
                .map(Subject::getId)
                .toList();

        return assignmentRepository.findAllBySubject_IdIn(subjectIds);
    }

    public List<Assignment> getAllAssignmentsOfCurrentUserBySubject(Long subjectId) {
        if (!authService.isStudentEnrolledInSubject(subjectId)) {
            throw new RuntimeException();
        }
        return assignmentRepository.findAllBySubject_Id(subjectId);
    }
}
