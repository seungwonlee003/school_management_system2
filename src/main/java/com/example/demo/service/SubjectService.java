package com.example.demo.service;

import com.example.demo.exception.UserNotEnrolledException;
import com.example.demo.model.*;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final AuthService authService;
    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    // only one role is assumed for a user
    public List<Subject> getAllSubjectsOfCurrentUser() {
        User user = authService.getCurrentUser();
        List<String> authorities = user.getRoles().stream().map(Authority::getName).toList();

        if (authorities.contains("ROLE_TEACHER")) {
            return authService.getTeacher().getSubjects().stream().toList();
        } else if (authorities.contains("ROLE_STUDENT")) {
            return authService.getStudent().getSubjects().stream().toList();
        } else {
            throw new RuntimeException("Unexpected role");
        }
    }

    public void enrollStudentInSubject(Long studentId, Long subjectId){
        Teacher teacher = authService.getTeacher();
        if(!authService.isTeacherEnrolledInSubject(subjectId)){
            throw new UserNotEnrolledException("teacher");
        }
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException());
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException());
        student.getSubjects().add(subject);
        studentRepository.save(student);
    }

    public Set<Student> getAllStudentsBySubject(Long subjectId){
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException());
        return subject.getStudents();
    }

    public void createSubject(Subject subject){
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId){
        subjectRepository.deleteById(subjectId);
    }
}
