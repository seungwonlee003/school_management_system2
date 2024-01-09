package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    private final AuthService authService;

    // student, teacher
    public List<Subject> getAllSubjectsByUser() {
        User user = authService.getCurrentUser();
        List<Authority> authorities = user.getRoles();

        if (authorities.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String userRole = authorities.get(0).getName();

        if ("TEACHER".equals(userRole)) {
            return authService.getTeacher().getSubjects().stream().toList(); // Assuming there's a getSubjects() method in Teacher entity

        } else if ("STUDENT".equals(userRole)) {
            return authService.getStudent().getSubjects().stream().toList(); // Assuming there's a getSubjects() method in Student entity

        } else {
            throw new RuntimeException("Unexpected role");
        }
    }


}
