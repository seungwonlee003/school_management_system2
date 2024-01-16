package com.example.demo.repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Page<Grade> findAllByStudent(Student student, Pageable pageable);

    List<Grade> findByAssignment(Assignment assignment);

    Optional<Grade> findByAssignmentAndStudent(Assignment assignment, Student student);

    List<Grade> findAllByAssignment(Assignment assignment);
}
