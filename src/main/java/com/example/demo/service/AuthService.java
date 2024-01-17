package com.example.demo.service;

import com.example.demo.model.Authority;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    // student, teacher
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalArgumentException();
        }
        return userRepository.findByName(authentication.getName()).orElseThrow(() -> new IllegalArgumentException());
    }

    // student, teacher
    public boolean isUserEnrolledInSubject(Long subjectId){
        User user = getCurrentUser();
        List<Authority> authorities = user.getRoles();

        if (authorities.get(0).getName().equals("ROLE_TEACHER")) {
            return getTeacher().getSubjects()
                    .stream()
                    .map(subject -> subject.getId())
                    .toList()
                    .contains(subjectId);
        } else if (authorities.get(0).getName().equals("ROLE_STUDENT")) {
            return getTeacher().getSubjects()
                    .stream()
                    .map(subject -> subject.getId())
                    .toList()
                    .contains(subjectId);
        } else {
            return false;
        }
    }

    // teacher
    public boolean isTeacherEnrolledInSubject(Long subjectId){
        return getTeacher().getSubjects()
                .stream()
                .map(subject -> subject.getId())
                .toList()
                .contains(subjectId);
    }

    // student
    public boolean isStudentEnrolledInSubject(Long subjectId){
        return getStudent().getSubjects()
                .stream()
                .map(subject -> subject.getId())
                .toList()
                .contains(subjectId);
    }

    // student
    public Student getStudent(){
        return studentRepository.findByUser_Id(getCurrentUser().getId()).orElseThrow(() -> new IllegalArgumentException());
    }

    // teacher
    public Teacher getTeacher(){
        return teacherRepository.findByUser_Id(getCurrentUser().getId()).orElseThrow(() -> new IllegalArgumentException());
    }


}
