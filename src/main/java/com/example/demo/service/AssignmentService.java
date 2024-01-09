package com.example.demo.service;

import com.example.demo.model.Assignment;
import com.example.demo.model.Subject;
import com.example.demo.model.Teacher;
import com.example.demo.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final SubjectService subjectService;

    private final AuthService authService;

    // student
    public List<Assignment> getAllAssignmentsBySubject(Long subjectId) {
       // checks if the user is enrolled in the subject
        if(!authService.isStudentEnrolledInSubject(subjectId)){
           throw new RuntimeException();
       }
        return assignmentRepository.findAllBySubjectId(subjectId)
                .stream()
                .toList();
    }

    // student, teacher
    public List<Assignment> getAllAssignmentsByUser(){
        List<Subject> subjects = subjectService.getAllSubjectsByUser();
        return subjects.stream()
                .flatMap(subject -> assignmentRepository.findAllBySubjectId(subject.getId()).stream())
                .toList();
    }

    // teacher
    public void createAssignment(Assignment assignment){
        Teacher teacher = authService.getTeacher();
        if(authService.isTeacherEnrolledInSubject(assignment.getSubject().getId())) {
            assignmentRepository.save(assignment);
        }
    }
}
