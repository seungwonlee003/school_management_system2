package com.example.demo.repository;

import com.example.demo.model.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllBySubject_Id(Long subjectId);

    Page<Assignment> findAllBySubject_Id(Long subjectId, Pageable pageable);

    Page<Assignment> findAllBySubject_IdIn(List<Long> subjectIds, Pageable pageable);

    List<Assignment> findAllBySubject_IdIn(List<Long> subjectIds);


}
