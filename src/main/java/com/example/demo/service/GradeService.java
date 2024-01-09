package com.example.demo.service;

import com.example.demo.model.Assignment;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final AuthService authService;
    private final GradeRepository gradeRepository;
    private final AssignmentService assignmentService;

    // student
    public List<Grade> getAllGradesForCurrentStudent() {
        Student student = authService.getStudent();
        return gradeRepository.findAllByStudent(student);
    }

    // student
    public List<Grade> getAllGradesForStudentInSubject(Long subjectId){
        return assignmentService.getAllAssignmentsBySubject(subjectId)
                .stream()
                .map(assignment -> gradeRepository.findByAssignment(assignment))
                .toList();
    }

}
