package com.example.demo.service;

import com.example.demo.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final AuthService authService;

    public List<Subject> getAllSubjectsOfCurrentUser() {
        User user = authService.getCurrentUser();
        List<Authority> authorities = user.getRoles();

        if (authorities.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String userRole = authorities.get(0).getName();

        if ("TEACHER".equals(userRole)) {
            return authService.getTeacher().getSubjects().stream().toList();
        } else if ("STUDENT".equals(userRole)) {
            return authService.getStudent().getSubjects().stream().toList();
        } else {
            throw new RuntimeException("Unexpected role");
        }
    }


}
