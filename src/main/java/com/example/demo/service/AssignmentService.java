package com.example.demo.service;

import com.example.demo.exception.AssignmentNotFoundException;
import com.example.demo.exception.UserNotEnrolledException;
import com.example.demo.model.*;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final SubjectService subjectService;

    private final AuthService authService;

    private final GradeRepository gradeRepository;

    private final SubjectRepository subjectRepository;

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
            throw new UserNotEnrolledException("Student");
        }
        Sort sort = Sort.by(sortBy);

        PageRequest pageable = PageRequest.of(offset, pageSize, sort);

        return assignmentRepository.findAllBySubject_Id(subjectId, pageable);
    }

    @Transactional
    public void createAssignment(Assignment assignment) {
        Teacher teacher = authService.getTeacher();

        if (!authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            throw new UserNotEnrolledException("Teacher");
        }
        assignmentRepository.save(assignment);

        // somehow assignment.getSubject().getStudents() not working... although student is eagerly fetched in subject entity
        Set<Student> allStudents = subjectRepository.findById(assignment.getSubject().getId())
                .orElseThrow(() -> new IllegalArgumentException())
                .getStudents();

        allStudents.forEach(student -> {
            Grade grade = new Grade();
            grade.setAssignment(assignment);
            grade.setStudent(student);
            gradeRepository.save(grade);
        });
    }

    @Transactional
    public void deleteAssignment(Long assignmentId) {
        Teacher teacher = authService.getTeacher();
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));
        if (!authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            throw new UserNotEnrolledException("Teacher");
        }
        assignmentRepository.deleteById(assignmentId);
    }

    // without pagination
    public List<Assignment> getAllAssignmentsOfCurrentUser() {
        List<Subject> subjects = subjectService.getAllSubjectsOfCurrentUser();

        List<Long> subjectIds = subjects.stream()
                .map(Subject::getId)
                .toList();

        return assignmentRepository.findAllBySubject_IdIn(subjectIds);
    }

    // without pagination
    public List<Assignment> getAllAssignmentsOfCurrentUserBySubject(Long subjectId) {
        if (!authService.isStudentEnrolledInSubject(subjectId)) {
            throw new UserNotEnrolledException("Student");
        }
        return assignmentRepository.findAllBySubject_Id(subjectId);
    }
}
