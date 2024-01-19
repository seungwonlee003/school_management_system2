package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final AuthService authService;
    private final SubjectRepository subjectRepository;

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

    public void createSubject(Subject subject){
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId){
        subjectRepository.deleteById(subjectId);
    }
}
