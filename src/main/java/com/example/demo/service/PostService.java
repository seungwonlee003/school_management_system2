package com.example.demo.service;

import com.example.demo.dto.PostRequest;
import com.example.demo.exception.UserNotEnrolledException;
import com.example.demo.model.Post;
import com.example.demo.model.Subject;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final SubjectService subjectService;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    public Page<Post> getAllPostsOfCurrentUser(int offset, int pageSize, String sortBy){
        List<Subject> subjects = subjectService.getAllSubjectsOfCurrentUser();

        List<Long> subjectIds = subjects.stream()
                .map(Subject::getId)
                .toList();

        Sort sort = Sort.by(sortBy);

        PageRequest pageable = PageRequest.of(offset, pageSize, sort);

        return postRepository.findAllBySubject_IdIn(subjectIds, pageable);
    }

    public Page<Post> getAllPostsOfCurrentUserBySubject(int offset, int pageSize, String sortBy, Long subjectId){
        if (!authService.isStudentEnrolledInSubject(subjectId)) {
            throw new UserNotEnrolledException("Student");
        }
        Sort sort = Sort.by(sortBy);

        PageRequest pageable = PageRequest.of(offset, pageSize, sort);

        return postRepository.findAllBySubject_Id(subjectId, pageable);
    }

    public void createPost(PostRequest postRequest){
        User user = authService.getCurrentUser();
        Subject subject = subjectRepository.findById(postRequest.getSubjectId()).orElseThrow(() -> new IllegalArgumentException());
        if(!authService.isUserEnrolledInSubject(subject.getId())){
            throw new UserNotEnrolledException("User");
        }
        Post post = new Post();
        post.setName(postRequest.getName());
        post.setDescription(postRequest.getDescription());
        post.setSubject(subject);
        post.setUser(user);
        postRepository.save(post);
    }

    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException());
        if(authService.getCurrentUser() != post.getUser()){
            throw new RuntimeException();
        }
        postRepository.deleteById(postId);
    }
}
