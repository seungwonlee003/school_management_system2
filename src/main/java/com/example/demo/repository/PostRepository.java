package com.example.demo.repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubject_Id(Long subjectId);

    Page<Post> findAllBySubject_Id(Long subjectId, Pageable pageable);

    Page<Post> findAllBySubject_IdIn(List<Long> subjectIds, Pageable pageable);

    List<Post> findAllBySubject_IdIn(List<Long> subjectIds);
}
